package com.fiap.data.repository

import com.fiap.data.local.datasource.BiometricsLocalDataSource
import com.hitg.domain.entity.Biometrics
import com.hitg.domain.entity.BiometricsInstanceState
import com.hitg.domain.entity.RequestState
import com.hitg.domain.repository.BiometricsRepository

class BiometricsRepositoryImpl(
    private val biometricsLocalDataSource: BiometricsLocalDataSource,
) : BiometricsRepository {

    override suspend fun getBiometricsState(): RequestState<Biometrics> {
        return biometricsLocalDataSource.getBiometricsState()
    }

    override suspend fun dontAskBiometricsAgain(): RequestState<Biometrics> {
        return biometricsLocalDataSource.setBiometricsState(BiometricsInstanceState.DENIED)
    }

    override suspend fun biometricLoginRegistered(): RequestState<Biometrics> {
        return biometricsLocalDataSource.setBiometricsState(BiometricsInstanceState.IN_USE)
    }

    override suspend fun markBiometricsUnavailable(): RequestState<Biometrics> {
        return biometricsLocalDataSource.setBiometricsState(BiometricsInstanceState.UNAVAILABLE)
    }

    override suspend fun markBiometricsNotInUse(): RequestState<Biometrics> {
        return biometricsLocalDataSource.setBiometricsState(BiometricsInstanceState.NOT_IN_USE)
    }
}