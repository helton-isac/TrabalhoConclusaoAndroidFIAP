package com.hitg.domain.repository

import com.hitg.domain.entity.NewUser
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.User
import com.hitg.domain.entity.UserLogin

interface UserRepository {

    suspend fun getUserLogged(): RequestState<User>

    suspend fun doLogin(userLogin: UserLogin): RequestState<User>

    suspend fun create(newUser: NewUser): RequestState<User>
}
