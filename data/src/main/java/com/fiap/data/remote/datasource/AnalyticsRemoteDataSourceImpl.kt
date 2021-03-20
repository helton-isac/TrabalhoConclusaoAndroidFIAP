package com.fiap.data.remote.datasource

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent

class AnalyticsRemoteDataSourceImpl(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsRemoteDataSource {

    override suspend fun logAttemptToUseGoogleSignIn() {
        firebaseAnalytics.logEvent("attempt_google_signIn") {}
    }

    override suspend fun logAttemptToUseFacebookSignIn() {
        firebaseAnalytics.logEvent("attempt_facebook_signIn") {}
    }

    override suspend fun logSuccessfullyLogin() {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
            param("method", "e-mail_and_password")
        }
    }

    override suspend fun logSuccessfullySignUp() {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP) {
            param("method", "e-mail_and_password")
        }
    }

    override suspend fun logSuccessfullySignOut() {
        firebaseAnalytics.logEvent("sign_out") {}
    }

    override suspend fun logShareApp() {
        firebaseAnalytics.logEvent("share") {
            param("content_type", "text")
        }

    }
}