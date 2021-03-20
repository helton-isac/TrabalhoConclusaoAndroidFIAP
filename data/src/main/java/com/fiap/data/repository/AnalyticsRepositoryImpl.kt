package com.fiap.data.repository

import com.fiap.data.remote.datasource.AnalyticsRemoteDataSource
import com.hitg.domain.repository.AnalyticsRepository

class AnalyticsRepositoryImpl(
    private val analyticsRemoteDataSource: AnalyticsRemoteDataSource
) : AnalyticsRepository {
    override suspend fun logAttemptToUseGoogleSignIn() {
        analyticsRemoteDataSource.logAttemptToUseGoogleSignIn()
    }

    override suspend fun logAttemptToUseFacebookSignIn() {
        analyticsRemoteDataSource.logAttemptToUseFacebookSignIn()
    }

    override suspend fun logSuccessfullyLogin() {
        analyticsRemoteDataSource.logSuccessfullyLogin()
    }

    override suspend fun logSuccessfullySignUp() {
        analyticsRemoteDataSource.logSuccessfullySignUp()
    }

    override suspend fun logSuccessfullySignOut() {
        analyticsRemoteDataSource.logSuccessfullySignOut()
    }

    override suspend fun logShareApp() {
        analyticsRemoteDataSource.logShareApp()
    }
}