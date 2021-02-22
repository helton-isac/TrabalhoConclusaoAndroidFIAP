package com.fiap.meurole.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.User
import com.hitg.domain.entity.UserLogin
import com.hitg.domain.usecases.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    val loginState = MutableLiveData<RequestState<User>>()

    fun doLogin(email: String, password: String) {
        viewModelScope.launch {
            loginState.value = loginUseCase.doLogin(UserLogin(email, password))
        }
    }
}