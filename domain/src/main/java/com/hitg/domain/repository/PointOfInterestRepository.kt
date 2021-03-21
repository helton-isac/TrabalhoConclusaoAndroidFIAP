package com.hitg.domain.repository

import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState

interface PointOfInterestRepository {
    suspend fun create(poi: PointOfInterest): RequestState<PointOfInterest>

    suspend fun fetch(idList: List<String>): RequestState<List<PointOfInterest>>

    suspend fun delete(id: String): RequestState<String>

    suspend fun edit(poi: PointOfInterest): RequestState<PointOfInterest>
}
