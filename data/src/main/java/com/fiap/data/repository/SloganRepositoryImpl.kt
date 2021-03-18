package com.fiap.data.repository

import com.fiap.data.remote.datasource.SloganRemoteDataSource
import com.hitg.domain.entity.RequestState
import com.hitg.domain.repository.SloganRepository

class SloganRepositoryImpl(
    private val sloganRemoteDataSource: SloganRemoteDataSource
) : SloganRepository {
    override suspend fun getSlogan(): RequestState<String> {
        return sloganRemoteDataSource.getSlogan()
    }
}