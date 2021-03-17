package com.hitg.domain.repository;

import com.hitg.domain.entity.PointOfInterest;
import com.hitg.domain.entity.RequestState;

interface PointOfInterestRepository {
    suspend fun create(poi: PointOfInterest): RequestState<PointOfInterest>
    suspend fun fetch(roadmapId: String): RequestState<List<PointOfInterest>>
}
