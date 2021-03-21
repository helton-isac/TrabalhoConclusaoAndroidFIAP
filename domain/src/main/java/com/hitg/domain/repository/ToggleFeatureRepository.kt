package com.hitg.domain.repository

import com.hitg.domain.entity.RequestState

interface ToggleFeatureRepository {
    suspend fun isGoogleSignInEnabled(): RequestState<Boolean>

    suspend fun isFacebookSignInEnabled(): RequestState<Boolean>
}