package com.fiap.data.remote.datasource

import com.hitg.domain.entity.RequestState

interface SloganRemoteDataSource {

    suspend fun getSlogan(): RequestState<String>
}
