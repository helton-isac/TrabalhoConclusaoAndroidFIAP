package com.fiap.data.remote.datasource

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.hitg.domain.entity.RequestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi

class SloganRemoteDataSourceImpl(
    private val firebaseRemoteConfig: FirebaseRemoteConfig
) : SloganRemoteDataSource {

    override suspend fun getSlogan(): RequestState<String> {

        val remoteConfig = firebaseRemoteConfig

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
            fetchTimeoutInSeconds = 60
        }

        val map = mapOf("slogan" to "O seu rolê é o que nos move!")
        remoteConfig.setDefaultsAsync(map)
        remoteConfig.setConfigSettingsAsync(configSettings)

        // TODO: Check if it is debug or release
        // if (BuildConfig.DEBUG) 0L else 720L
        val cacheExpiration = 720L
        remoteConfig.fetch(cacheExpiration).await()
        remoteConfig.activate().await()

        val value = remoteConfig.getString("slogan")

        return RequestState.Success(value)
    }
}
