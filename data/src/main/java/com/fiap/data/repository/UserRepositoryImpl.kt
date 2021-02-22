package com.fiap.data.repository

import com.fiap.data.remote.datasource.UserRemoteDataSource
import com.hitg.domain.entity.NewUser
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.User
import com.hitg.domain.entity.UserLogin
import com.hitg.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun getUserLogged(): RequestState<User> {
        return userRemoteDataSource.getUserLogged()
    }

    override suspend fun doLogin(userLogin: UserLogin): RequestState<User> {
        return userRemoteDataSource.doLogin(userLogin)
    }

    override suspend fun create(newUser: NewUser): RequestState<User> {
        return userRemoteDataSource.create(newUser)
    }
}