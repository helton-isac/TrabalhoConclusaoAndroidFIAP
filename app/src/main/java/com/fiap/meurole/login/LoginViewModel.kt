package com.fiap.meurole.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitg.domain.entity.Biometrics
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.User
import com.hitg.domain.entity.UserLogin
import com.hitg.domain.usecases.BiometricsUseCase
import com.hitg.domain.usecases.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val biometricsUseCase: BiometricsUseCase,
) : ViewModel() {

    val loginState = MutableLiveData<RequestState<User>>()
    val biometricsState = MutableLiveData<RequestState<Biometrics>>()

    fun doLogin(email: String, password: String) {
        viewModelScope.launch {
            loginState.value = loginUseCase.doLogin(UserLogin(email, password))
        }
    }

    fun checkForBiometrics() {
        viewModelScope.launch {
            biometricsState.value = biometricsUseCase.getBiometricsState()
        }
    }

    fun registerBiometricsForUser(email: String, password: String) {
        viewModelScope.launch {
            biometricsState.value =
                biometricsUseCase.registerBiometricsForUser(UserLogin(email, password))
        }
    }
}