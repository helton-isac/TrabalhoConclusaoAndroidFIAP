package com.hitg.domain.repository

import com.hitg.domain.entity.Biometrics
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.UserLogin

interface BiometricsRepository {

    suspend fun registerBiometrics(userLogin: UserLogin): RequestState<Biometrics>

    suspend fun getBiometricsState(): RequestState<Biometrics>

}