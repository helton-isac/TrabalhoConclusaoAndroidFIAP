package com.hitg.domain.usecases

import com.hitg.domain.entity.RequestState
import com.hitg.domain.repository.ToggleFeatureRepository


class ToggleFeatureUseCase(
    private val toggleFeatureRepository: ToggleFeatureRepository
) {

    suspend fun isGoogleSignInEnabled(): RequestState<Boolean> {
        return toggleFeatureRepository.isGoogleSignInEnabled()
    }

    suspend fun isFacebookSignInEnabled(): RequestState<Boolean> {
        return toggleFeatureRepository.isFacebookSignInEnabled()
    }
}