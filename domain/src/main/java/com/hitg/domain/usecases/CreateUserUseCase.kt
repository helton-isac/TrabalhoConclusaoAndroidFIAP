package com.hitg.domain.usecases

import com.hitg.domain.entity.NewUser
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.User
import com.hitg.domain.repository.UserRepository

class CreateUserUseCase(
    private val userRepository: UserRepository
) {

    suspend fun create(newUser: NewUser): RequestState<User> =
        userRepository.create(newUser)
}
