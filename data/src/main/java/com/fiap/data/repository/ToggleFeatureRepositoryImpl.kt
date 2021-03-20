package com.fiap.data.repository

import com.fiap.data.remote.datasource.RemoteConfigDataSource
import com.hitg.domain.entity.RequestState
import com.hitg.domain.repository.ToggleFeatureRepository


class ToggleFeatureRepositoryImpl(
    private val remoteConfigDataSource: RemoteConfigDataSource
) : ToggleFeatureRepository {
    override suspend fun isGoogleSignInEnabled(): RequestState<Boolean> {
        return remoteConfigDataSource.isGoogleSignInEnabled()
    }

    override suspend fun isFacebookSignInEnabled(): RequestState<Boolean> {
        return remoteConfigDataSource.isFacebookSignInEnabled()
    }
}