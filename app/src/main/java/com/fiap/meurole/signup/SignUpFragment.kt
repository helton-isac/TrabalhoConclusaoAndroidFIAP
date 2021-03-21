package com.fiap.meurole.signup

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.NavHostFragment
import com.fiap.meurole.R
import com.fiap.meurole.base.BaseFragment
import com.fiap.meurole.utils.DialogUtils
import com.hitg.domain.entity.RequestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class SignUpFragment : BaseFragment() {

    override val layout = R.layout.sign_up_fragment

    private val signUpViewModel: SignUpViewModel by viewModel()

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btCreateAccount: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(view)

        registerObserver()
    }

    private fun setUpView(view: View) {

        etName = view.findViewById(R.id.etName)
        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        btCreateAccount = view.findViewById(R.id.btCreateAccount)
        setUpListener()
    }

    private fun setUpListener() {
        btCreateAccount.setOnClickListener {
            signUpViewModel.create(
                etName.text.toString(),
                etEmail.text.toString(),
                etPassword.text.toString()
            )
        }
    }

    private fun registerObserver() {
        this.signUpViewModel.newUserState.observe(viewLifecycleOwner, {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.homeFragment)
                }
                is RequestState.Error -> {
                    hideLoading()
                    DialogUtils.showToastErrorMessage(requireContext(), it.throwable.message)
                }
                is RequestState.Loading -> showLoading("Realizando a autenticação")
            }
        })
    }


}