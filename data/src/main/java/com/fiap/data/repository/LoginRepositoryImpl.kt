package com.fiap.data.repository

import com.fiap.data.datasource.LoginDataSourceImpl
import com.hitg.domain.entity.LoggedInUser
import com.hitg.domain.entity.RequestState
import com.hitg.domain.repository.LoginRepository

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepositoryImpl(val dataSource: LoginDataSourceImpl) : LoginRepository {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    override fun logout() {
        user = null
        dataSource.logout()
    }

    override fun login(username: String, password: String): RequestState<LoggedInUser> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is RequestState.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}