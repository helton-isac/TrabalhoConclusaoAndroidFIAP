package com.hitg.domain.usecases

import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.User
import com.hitg.domain.entity.UserLogin
import com.hitg.domain.exception.EmptyEmailException
import com.hitg.domain.exception.EmptyPasswordException
import com.hitg.domain.repository.UserRepository

class LoginUseCase(
    private val userRepository: UserRepository
) {

    suspend fun doLogin(userLogin: UserLogin): RequestState<User> {

        if (userLogin.email.isBlank()) {
            RequestState.Error(EmptyEmailException())
        }

        if (userLogin.password.isBlank()) {
            RequestState.Error(EmptyPasswordException())
        }

        return userRepository.doLogin(userLogin)

    }

    suspend fun doLogout(): RequestState<Boolean> {
        return userRepository.doLogout()
    }

}