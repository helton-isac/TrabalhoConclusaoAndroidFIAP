package com.fiap.data.datasource

import com.hitg.domain.entity.LoggedInUser
import com.hitg.domain.entity.RequestState

interface LoginDataSource {
    fun login(username: String, password: String): RequestState<LoggedInUser>
    fun logout()
}