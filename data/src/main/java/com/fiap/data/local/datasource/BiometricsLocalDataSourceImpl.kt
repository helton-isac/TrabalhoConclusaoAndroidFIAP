package com.fiap.data.local.datasource

import android.content.SharedPreferences
import com.hitg.domain.entity.Biometrics
import com.hitg.domain.entity.BiometricsInstanceState
import com.hitg.domain.entity.RequestState

class BiometricsLocalDataSourceImpl(
    private val sharedPreferences: SharedPreferences
) : BiometricsLocalDataSource {

    companion object {
        const val BIOMETRIC_INSTANCE_STATE = "BIOMETRIC_INSTANCE_STATE"
        fun valueOf(value: Int): BiometricsInstanceState =
            BiometricsInstanceState.values().find { it.value == value }!!
    }

    override suspend fun getBiometricsState(): RequestState<Biometrics> {
        val defaultValue = BiometricsInstanceState.NOT_IN_USE.value
        val biometricsInstanceState =
            sharedPreferences.getInt(BIOMETRIC_INSTANCE_STATE, defaultValue)
        return RequestState.Success(Biometrics(state = valueOf(biometricsInstanceState)))
    }

    override suspend fun setBiometricsState(biometricsInstanceState: BiometricsInstanceState): RequestState<Biometrics> {
        with(sharedPreferences.edit()) {
            putInt(BIOMETRIC_INSTANCE_STATE, biometricsInstanceState.value)
            commit()
        }
        return RequestState.Success(Biometrics(state = valueOf(biometricsInstanceState.value)))
    }
}