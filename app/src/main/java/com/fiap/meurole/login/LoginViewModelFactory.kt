package com.fiap.meurole.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hitg.domain.usecases.LoginUseCase

class LoginViewModelFactory(
    private val loginUseCase: LoginUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            LoginUseCase::class.java
        ).newInstance(loginUseCase)
    }

}