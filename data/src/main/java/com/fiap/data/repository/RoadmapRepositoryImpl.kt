package com.fiap.data.repository

import com.fiap.data.remote.datasource.RoadmapRemoteDataSource
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import com.hitg.domain.repository.RoadmapRepository

class RoadmapRepositoryImpl(
    private val roadmapRemoteDataSource: RoadmapRemoteDataSource
): RoadmapRepository {

    override suspend fun create(roadmap: Roadmap): RequestState<Roadmap> {
        return roadmapRemoteDataSource.create(roadmap)
    }

}