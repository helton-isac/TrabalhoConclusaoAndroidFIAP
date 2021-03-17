package com.fiap.data.remote.datasource

import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState

interface PointOfInterestRemoteDataSource {

    suspend fun create(poi: PointOfInterest): RequestState<PointOfInterest>

    suspend fun fetch(roadmapId: String): RequestState<List<PointOfInterest>>

}