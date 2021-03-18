package com.fiap.data.remote.datasource

import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState

interface PointOfInterestRemoteDataSource {

    suspend fun create(poi: PointOfInterest): RequestState<PointOfInterest>

    suspend fun fetch(idList: List<String>): RequestState<List<PointOfInterest>>

    suspend fun delete(id: String): RequestState<String>

    suspend fun edit(poi: PointOfInterest): RequestState<PointOfInterest>

}