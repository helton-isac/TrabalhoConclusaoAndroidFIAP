package com.fiap.meurole.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.User
import com.hitg.domain.usecases.GetUserLoggedUseCase
import com.hitg.domain.usecases.LoginUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserLoggedUseCase: GetUserLoggedUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    val userLoggedState = MutableLiveData<RequestState<User>>()

    val logoutResponse = MutableLiveData<RequestState<Boolean>>()

    fun doLogout() {
        viewModelScope.launch {
            logoutResponse.value = RequestState.Loading
            logoutResponse.value = loginUseCase.doLogout()
        }
    }

    fun getUserLogged() {
        viewModelScope.launch {
            userLoggedState.value = RequestState.Loading
            userLoggedState.value = getUserLoggedUseCase.getUserLogged()
        }
    }
}