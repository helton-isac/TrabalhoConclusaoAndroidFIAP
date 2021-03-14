package com.hitg.domain.usecases

import com.hitg.domain.entity.Biometrics
import com.hitg.domain.entity.RequestState
import com.hitg.domain.repository.BiometricsRepository

class BiometricsUseCase(
    private val biometricsRepository: BiometricsRepository
) {

    suspend fun getBiometricsState(): RequestState<Biometrics> {
        return biometricsRepository.getBiometricsState()
    }

    suspend fun biometricLoginRegistered(): RequestState<Biometrics> {
        return biometricsRepository.biometricLoginRegistered()
    }

    suspend fun dontAskBiometricsAgain(): RequestState<Biometrics> {
        return biometricsRepository.dontAskBiometricsAgain()
    }

    suspend fun markBiometricsUnavailable(): RequestState<Biometrics>? {
        return biometricsRepository.markBiometricsUnavailable()
    }

    suspend fun markBiometricsNotInUse(): RequestState<Biometrics>? {
        return biometricsRepository.markBiometricsNotInUse()
    }
}