package com.fiap.data.repository

import com.fiap.data.local.datasource.BiometricsLocalDataSource
import com.hitg.domain.entity.Biometrics
import com.hitg.domain.entity.BiometricsInstanceState
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.UserLogin
import com.hitg.domain.repository.BiometricsRepository

class BiometricsRepositoryImpl(
    private val biometricsLocalDataSource: BiometricsLocalDataSource
) : BiometricsRepository {

    override suspend fun registerBiometrics(userLogin: UserLogin): RequestState<Biometrics> {
        return biometricsLocalDataSource.setBiometricsState(BiometricsInstanceState.IN_USE)
    }

    override suspend fun getBiometricsState(): RequestState<Biometrics> {
        return biometricsLocalDataSource.getBiometricsState()
    }
}