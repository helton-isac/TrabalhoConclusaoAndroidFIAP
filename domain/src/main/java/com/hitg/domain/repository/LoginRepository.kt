package com.hitg.domain.repository

import com.hitg.domain.entity.LoggedInUser
import com.hitg.domain.entity.RequestState

interface LoginRepository {
    fun login(username: String, password: String): RequestState<LoggedInUser>
    fun logout()
}