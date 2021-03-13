package com.fiap.meurole.login

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fiap.meurole.R
import com.fiap.meurole.base.BaseFragment
import com.fiap.meurole.base.auth.NAVIGATION_KEY
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

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(view)
        registerObserver()
        registerBackPressedAction()
    }

    private fun setUpView(view: View) {
        btLogin = view.findViewById(R.id.btLogin)
        etEmailLogin = view.findViewById(R.id.etEmailLogin)
        etPasswordLogin = view.findViewById(R.id.etPasswordLogin)
        tvNewAccount = view.findViewById(R.id.tvNewAccount)

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
                        IN_USE -> finishLoginSuccessful()
                        DENIED -> finishLoginSuccessful()
                        NOT_IN_USE -> promptUserToUseBiometrics()
                    }
                }

                is RequestState.Error -> showError(biometric.throwable)
                is RequestState.Loading -> showLoading(getString(R.string.processing))
            }
        })
    }

    private fun promptUserToUseBiometrics() {
        hideLoading()
        val dialogClickListener =
            DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        loginViewModel.registerBiometricsForUser(
                            etEmailLogin.text.toString(),
                            etPasswordLogin.text.toString()
                        )
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        finishLoginSuccessful()
                    }
                }
            }

        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.register_biometric_login))
                .setPositiveButton(getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getString(R.string.no), dialogClickListener).show()
        }
    }

    private fun showSuccess() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
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