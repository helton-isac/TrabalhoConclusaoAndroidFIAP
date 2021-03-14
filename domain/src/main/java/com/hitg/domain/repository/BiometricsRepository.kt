package com.hitg.domain.repository

import com.hitg.domain.entity.Biometrics
import com.hitg.domain.entity.RequestState

interface BiometricsRepository {

    suspend fun getBiometricsState(): RequestState<Biometrics>

    suspend fun dontAskBiometricsAgain(): RequestState<Biometrics>

    suspend fun biometricLoginRegistered(): RequestState<Biometrics>

    suspend fun markBiometricsUnavailable(): RequestState<Biometrics>?

    suspend fun markBiometricsNotInUse(): RequestState<Biometrics>?
}