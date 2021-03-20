package com.fiap.data.remote.datasource

interface AnalyticsRemoteDataSource {
    suspend fun logAttemptToUseGoogleSignIn()

    suspend fun logAttemptToUseFacebookSignIn()

    suspend fun logSuccessfullyLogin()

    suspend fun logSuccessfullySignUp()

    suspend fun logSuccessfullySignOut()

    suspend fun logShareApp()
}