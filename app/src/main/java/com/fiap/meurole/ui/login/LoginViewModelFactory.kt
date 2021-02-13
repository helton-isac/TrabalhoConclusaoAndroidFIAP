package com.fiap.meurole.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fiap.data.datasource.LoginDataSourceImpl
import com.fiap.data.repository.LoginRepositoryImpl

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepositoryImpl(
                    dataSource = LoginDataSourceImpl()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}