package com.fiap.data.remote.datasource

import com.hitg.domain.entity.LoggedInUser
import com.hitg.domain.entity.RequestState
import java.io.IOException
import java.util.*

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSourceImpl : LoginDataSource {

    override fun login(username: String, password: String): RequestState<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(UUID.randomUUID().toString(), "Jane Doe")
            return RequestState.Success(fakeUser)
        } catch (e: Throwable) {
            return RequestState.Error(IOException("Error logging in", e))
        }
    }

    override fun logout() {
        // TODO: revoke authentication
    }
}