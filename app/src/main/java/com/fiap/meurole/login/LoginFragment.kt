package com.fiap.meurole.login

import android.content.DialogInterface
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fiap.meurole.R
import com.fiap.meurole.base.BaseFragment
import com.fiap.meurole.base.auth.NAVIGATION_KEY
import com.hhlr.biometrics.BiometricsApi
import com.hitg.domain.entity.Biometrics
import com.hitg.domain.entity.BiometricsInstanceState.*
import com.hitg.domain.entity.RequestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class LoginFragment : BaseFragment() {

    override val layout = R.layout.login_fragment

    private lateinit var btLogin: Button
    private lateinit var etEmailLogin: EditText
    private lateinit var etPasswordLogin: EditText
    private lateinit var tvNewAccount: TextView
    private lateinit var tvBiometrics: TextView

    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var biometricsApi: BiometricsApi

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        biometricsApi = BiometricsApi(requireContext())

        setUpView(view)
        registerObserver()
        registerBackPressedAction()
        checkBiometrics()

    }

    private fun checkBiometrics() {
        if (biometricsApi.canAuthenticate()) {
            loginViewModel.checkForBiometrics()
        } else {
            loginViewModel.markBiometricsUnavailable()
        }
    }

    private fun setUpView(view: View) {
        btLogin = view.findViewById(R.id.btLogin)
        etEmailLogin = view.findViewById(R.id.etEmailLogin)
        etPasswordLogin = view.findViewById(R.id.etPasswordLogin)
        tvNewAccount = view.findViewById(R.id.tvNewAccount)
        tvBiometrics = view.findViewById(R.id.tvBiometrics)

        btLogin.setOnClickListener {
            showLoading()
            loginViewModel.doLogin(
                etEmailLogin.text.toString(),
                etPasswordLogin.text.toString()
            )
        }
        tvNewAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        tvBiometrics.setOnClickListener {
            promptBiometrics()
        }
    }

    private fun registerObserver() {
        loginViewModel.loginState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> showSuccess()
                is RequestState.Error -> showError(it.throwable)
                is RequestState.Loading -> showLoading(getString(R.string.authenticating))
            }
        })
        loginViewModel.biometricsState.observe(viewLifecycleOwner, Observer { biometric ->
            when (biometric) {
                is RequestState.Success -> {
                    when (biometric.data.state) {
                        IN_USE -> {
                            if (loginViewModel.loginState.value is RequestState.Success<*>) {
                                finishLoginSuccessful()
                            } else {
                                promptBiometrics()
                            }
                        }
                        DENIED -> {
                            if (loginViewModel.loginState.value is RequestState.Success<*>) {
                                finishLoginSuccessful()
                            }
                        }
                        NOT_IN_USE -> {
                            tvBiometrics.visibility = View.GONE
                            if (loginViewModel.loginState.value is RequestState.Success<*>) {
                                promptUserToUseBiometrics()
                            }
                        }
                        UNAVAILABLE -> {
                            tvBiometrics.visibility = View.GONE
                        }
                    }
                }
                is RequestState.Error -> showError(biometric.throwable)
                is RequestState.Loading -> showLoading(getString(R.string.processing))
            }
        })
    }

    private fun promptBiometrics() {


        val value = loginViewModel.biometricsState.value
        if (value is RequestState.Success<Biometrics>) {
            if (value.data.state == DENIED) {
                loginViewModel.markBiometricsNotInUse()
            } else {
                biometricsApi.showBiometricPromptForDecryption(
                    activityContext = activity as AppCompatActivity,
                    onAuthenticationSucceeded = { plainText ->
                        showLoading()
                        val values = plainText.split(":")
                        val user = String(Base64.decode(values[0], Base64.DEFAULT))
                        val pass = String(Base64.decode(values[1], Base64.DEFAULT))
                        loginViewModel.doLogin(user, pass)
                    },
                    onAuthenticationError = {
                        showError(Exception("Failed to use Biometrics try again on next login"))
                        hideLoading()
                    },
                    onAuthenticationFailed = {
                        showError(Exception("Failed to use Biometrics try again on next login"))
                        hideLoading()
                    }
                )
            }
        }
    }

    private fun promptUserToUseBiometrics() {
        hideLoading()
        val dialogClickListener =
            DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        val encoded64Key: String = Base64.encodeToString(
                            etEmailLogin.text.toString().toByteArray(),
                            Base64.DEFAULT
                        )
                        val encoded64Value: String = Base64.encodeToString(
                            etPasswordLogin.text.toString().toByteArray(),
                            Base64.DEFAULT
                        )
                        val token = "$encoded64Key:$encoded64Value"
                        biometricsApi.showBiometricPromptForEncription(
                            activityContext = activity as AppCompatActivity,
                            token,
                            onAuthenticationSucceeded = {
                                showLoading()
                                loginViewModel.biometricLoginRegistered()
                            },
                            onAuthenticationError = {
                                showError(Exception("Failed to use Biometrics try again on next login"))
                                finishLoginSuccessful()
                            },
                            onAuthenticationFailed = {
                                showError(Exception("Failed to use Biometrics try again on next login"))
                                finishLoginSuccessful()
                            }
                        )
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        loginViewModel.dontAskForBiometrics()
                    }
                    DialogInterface.BUTTON_NEUTRAL -> {
                        if (loginViewModel.loginState.value is RequestState.Success<*>) {
                            finishLoginSuccessful()
                        }
                    }
                }
            }

        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.register_biometric_login))
                .setPositiveButton(getString(R.string.yes), dialogClickListener)
                .setNeutralButton(getString(R.string.later), dialogClickListener)
                .setNegativeButton(getString(R.string.dont_ask_again), dialogClickListener).show()
        }
    }

    private fun showSuccess() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M
            && biometricsApi.canAuthenticate()
        ) {
            loginViewModel.checkForBiometrics()
        } else {
            finishLoginSuccessful()
        }
    }

    private fun finishLoginSuccessful() {
        hideLoading()
        val navIdForArguments = arguments?.getInt(NAVIGATION_KEY)
        if (navIdForArguments == null) {
            findNavController().navigate(R.id.main_nav_graph)
        } else {
            findNavController().popBackStack(navIdForArguments, false)
        }
    }

    // TODO: Prompt a better message dialog to the user
    private fun showError(throwable: Throwable) {
        hideLoading()

        etEmailLogin.error = null
        etPasswordLogin.error = null

        showMessage(throwable.message)
    }

    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}