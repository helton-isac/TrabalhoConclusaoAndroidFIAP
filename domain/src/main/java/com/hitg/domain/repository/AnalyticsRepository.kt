package com.hitg.domain.repository

interface AnalyticsRepository {
    suspend fun logAttemptToUseGoogleSignIn()

    suspend fun logAttemptToUseFacebookSignIn()

    suspend fun logSuccessfullyLogin()

    suspend fun logSuccessfullySignUp()

    suspend fun logSuccessfullySignOut()

    suspend fun logShareApp()
}