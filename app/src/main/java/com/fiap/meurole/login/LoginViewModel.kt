package com.fiap.meurole.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitg.domain.entity.Biometrics
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.User
import com.hitg.domain.entity.UserLogin
import com.hitg.domain.usecases.BiometricsUseCase
import com.hitg.domain.usecases.LogAnalyticsEventUseCase
import com.hitg.domain.usecases.LoginUseCase
import com.hitg.domain.usecases.ToggleFeatureUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val biometricsUseCase: BiometricsUseCase,
    private val toggleFeatureUseCase: ToggleFeatureUseCase,
    private val logAnalyticsEvent: LogAnalyticsEventUseCase
) : ViewModel() {

    val loginState = MutableLiveData<RequestState<User>>()
    val biometricsState = MutableLiveData<RequestState<Biometrics>>()
    val isGoogleSignInEnabled = MutableLiveData<RequestState<Boolean>>()
    val isFacebookSignInEnabled = MutableLiveData<RequestState<Boolean>>()

    init {
        isGoogleSignInEnabled.value = RequestState.Success(false)
        isFacebookSignInEnabled.value = RequestState.Success(false)
    }

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

    fun dontAskForBiometrics() {
        viewModelScope.launch {
            biometricsState.value =
                biometricsUseCase.dontAskBiometricsAgain()
        }
    }

    fun biometricLoginRegistered() {
        viewModelScope.launch {
            biometricsState.value =
                biometricsUseCase.biometricLoginRegistered()
        }
    }

    fun markBiometricsUnavailable() {
        viewModelScope.launch {
            biometricsState.value =
                biometricsUseCase.markBiometricsUnavailable()
        }
    }

    fun markBiometricsNotInUse() {
        viewModelScope.launch {
            biometricsState.value =
                biometricsUseCase.markBiometricsNotInUse()
        }
    }

    fun isGoogleSignInEnabled() {
        viewModelScope.launch {
            isGoogleSignInEnabled.value = toggleFeatureUseCase.isGoogleSignInEnabled()
        }
    }

    fun isFacebookSignInEnabled() {
        viewModelScope.launch {
            isFacebookSignInEnabled.value = toggleFeatureUseCase.isFacebookSignInEnabled()
        }

    }

    fun logAttemptToUseFacebookSignIn() {
        viewModelScope.launch {
            logAnalyticsEvent.logAttemptToUseFacebookSignIn()
        }
    }

    fun logAttemptToUseGoogleSignIn() {
        viewModelScope.launch {
            logAnalyticsEvent.logAttemptToUseGoogleSignIn()
        }
    }
}