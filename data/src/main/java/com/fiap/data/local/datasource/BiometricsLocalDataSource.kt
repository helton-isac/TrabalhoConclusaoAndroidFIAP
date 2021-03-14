package com.fiap.data.local.datasource

import com.hitg.domain.entity.Biometrics
import com.hitg.domain.entity.BiometricsInstanceState
import com.hitg.domain.entity.RequestState

interface BiometricsLocalDataSource {
    suspend fun getBiometricsState(): RequestState<Biometrics>
    suspend fun setBiometricsState(biometricsInstanceState: BiometricsInstanceState): RequestState<Biometrics>
}