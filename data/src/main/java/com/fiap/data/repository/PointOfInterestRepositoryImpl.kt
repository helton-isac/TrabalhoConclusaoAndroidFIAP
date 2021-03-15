package com.fiap.data.repository

import com.fiap.data.remote.datasource.PointOfInterestRemoteDataSource
import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import com.hitg.domain.repository.PointOfInterestRepository

class PointOfInterestRepositoryImpl(
    private val poiRemoteDataSource: PointOfInterestRemoteDataSource
): PointOfInterestRepository {

    override suspend fun create(poi: PointOfInterest): RequestState<PointOfInterest> {
        return poiRemoteDataSource.create(poi)
    }

}