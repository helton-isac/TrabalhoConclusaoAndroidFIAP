package com.fiap.meurole.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitg.domain.entity.NewUser
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.User
import com.hitg.domain.usecases.CreateUserUseCase
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {
    val newUserState = MutableLiveData<RequestState<User>>()

    fun create(name: String, email: String, password: String) {
        viewModelScope.launch {
            newUserState.value = createUserUseCase.create(
                NewUser(
                    name,
                    email,
                    password
                )
            )
        }
    }
}