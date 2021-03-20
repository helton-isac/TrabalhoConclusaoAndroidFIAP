package com.fiap.data.repository

import com.fiap.data.remote.datasource.RemoteConfigDataSource
import com.hitg.domain.entity.RequestState
import com.hitg.domain.repository.SloganRepository

class SloganRepositoryImpl(
    private val remoteConfigDataSource: RemoteConfigDataSource
) : SloganRepository {
    override suspend fun getSlogan(): RequestState<String> {
        return remoteConfigDataSource.getSlogan()
    }
}