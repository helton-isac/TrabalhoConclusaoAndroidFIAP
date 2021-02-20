package com.fiap.meurole.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fiap.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import com.fiap.data.repository.UserRepositoryImpl
import com.fiap.meurole.R
import com.fiap.meurole.base.BaseFragment
import com.fiap.meurole.base.auth.NAVIGATION_KEY
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hitg.domain.entity.RequestState
import com.hitg.domain.usecases.LoginUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class LoginFragment : BaseFragment() {

    override val layout = R.layout.login_fragment

    private lateinit var btLogin: Button
    private lateinit var etEmailLogin: EditText
    private lateinit var etPasswordLogin: EditText

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(
            this,
            LoginViewModelFactory(
                LoginUseCase(
                    UserRepositoryImpl(
                        UserRemoteFirebaseDataSourceImpl(
                            Firebase.auth,
                            Firebase.firestore
                        )
                    )
                )
            )
        ).get(LoginViewModel::class.java)
    }

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

        btLogin.setOnClickListener {
            loginViewModel.doLogin(
                etEmailLogin.text.toString(),
                etPasswordLogin.text.toString()
            )
        }
    }

    private fun registerObserver() {
        loginViewModel.loginState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> showSuccess()
                is RequestState.Error -> showError(it.throwable)
                is RequestState.Loading -> showLoading("Realizando a autenticação")
            }
        })
    }

    private fun showSuccess() {
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