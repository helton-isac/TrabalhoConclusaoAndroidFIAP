package com.fiap.data.remote.datasource

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.hitg.domain.entity.RequestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
class RemoteConfigDataSourceImpl(
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) : RemoteConfigDataSource {

    private val sloganProperty = "slogan" to "O seu rolê é o que nos move!"

    private val isFacebookSignInEnabled = "is_facebook_sign_in_enabled" to false
    private val isGoogleSignInEnabled = "is_google_sign_in_enabled" to false

    override suspend fun getSlogan(): RequestState<String> {
        val remoteConfig = getRemoteConfig()
        val value = remoteConfig.getString(sloganProperty.first)
        return RequestState.Success(value)
    }

    override suspend fun isFacebookSignInEnabled(): RequestState<Boolean> {
        val remoteConfig = getRemoteConfig()
        val value = remoteConfig.getBoolean(isFacebookSignInEnabled.first)
        return RequestState.Success(value)
    }

    override suspend fun isGoogleSignInEnabled(): RequestState<Boolean> {
        val remoteConfig = getRemoteConfig()
        val value = remoteConfig.getBoolean(isGoogleSignInEnabled.first)
        return RequestState.Success(value)
    }

    private suspend fun getRemoteConfig(): FirebaseRemoteConfig {
        val remoteConfig = firebaseRemoteConfig

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
            fetchTimeoutInSeconds = 60
        }

        val map = createDefaultMap()
        remoteConfig.setDefaultsAsync(map)
        remoteConfig.setConfigSettingsAsync(configSettings)

        // TODO: Check if it is debug or release
        // if (BuildConfig.DEBUG) 0L else 720L
        val cacheExpiration = 720L
        remoteConfig.fetch(cacheExpiration).await()
        remoteConfig.activate().await()
        return remoteConfig
    }

    private fun createDefaultMap(): Map<String, Any> {
        return mapOf(
            sloganProperty,
            isFacebookSignInEnabled,
            isGoogleSignInEnabled
        )
    }
}
