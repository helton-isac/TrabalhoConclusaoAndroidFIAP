package com.fiap.meurole.base.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.User
import com.hitg.domain.usecases.GetUserLoggedUseCase
import kotlinx.coroutines.launch

class BaseAuthViewModel(
    private val getUserLoggedUseCase: GetUserLoggedUseCase
) : ViewModel() {

    val userLoggedState = MutableLiveData<RequestState<User>>()

    fun getUserLogged() {
        viewModelScope.launch {
            userLoggedState.value = RequestState.Loading
            userLoggedState.value = getUserLoggedUseCase.getUserLogged()
        }
    }
}