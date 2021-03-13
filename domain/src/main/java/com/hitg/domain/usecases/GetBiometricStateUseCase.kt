package com.hitg.domain.usecases

import com.hitg.domain.entity.Biometrics
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.UserLogin
import com.hitg.domain.exception.EmptyEmailException
import com.hitg.domain.exception.EmptyPasswordException
import com.hitg.domain.repository.BiometricsRepository

class BiometricsUseCase(
    private val biometricsRepository: BiometricsRepository
) {

    suspend fun getBiometricsState(): RequestState<Biometrics> {
        return biometricsRepository.getBiometricsState()
    }

    suspend fun registerBiometricsForUser(userLogin: UserLogin): RequestState<Biometrics> {

        if (userLogin.email.isBlank()) {
            RequestState.Error(EmptyEmailException())
        }

        if (userLogin.password.isBlank()) {
            RequestState.Error(EmptyPasswordException())
        }

        return biometricsRepository.registerBiometrics(userLogin)
    }
}