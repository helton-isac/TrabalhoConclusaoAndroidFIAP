package com.hitg.domain.usecases

import com.hitg.domain.repository.AnalyticsRepository

class LogAnalyticsEventUseCase(
    private val analyticsRepository: AnalyticsRepository
) {
    suspend fun logAttemptToUseFacebookSignIn() {
        analyticsRepository.logAttemptToUseFacebookSignIn()
    }

    suspend fun logAttemptToUseGoogleSignIn() {
        analyticsRepository.logAttemptToUseGoogleSignIn()
    }

    suspend fun logShareApp() {
        analyticsRepository.logShareApp()
    }

    suspend fun logCreateRoadmap() {
        analyticsRepository.logCreateRoadmap()
    }
}