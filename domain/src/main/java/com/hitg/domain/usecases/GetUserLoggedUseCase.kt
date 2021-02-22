package com.hitg.domain.usecases

import com.hitg.domain.repository.UserRepository

class GetUserLoggedUseCase(
    private val userRepository: UserRepository
) {

    suspend fun getUserLogged() = userRepository.getUserLogged()
}