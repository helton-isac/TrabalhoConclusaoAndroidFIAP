package com.fiap.data.repository

import com.fiap.data.remote.datasource.RoadmapRemoteDataSource
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import com.hitg.domain.repository.RoadmapRepository

class RoadmapRepositoryImpl(
    private val roadmapRemoteDataSource: RoadmapRemoteDataSource
): RoadmapRepository {

    override suspend fun createOrEdit(roadmap: Roadmap): RequestState<Roadmap> {
        return roadmapRemoteDataSource.createOrEdit(roadmap)
    }

    override suspend fun fetch(): RequestState<List<Roadmap>> {
        return roadmapRemoteDataSource.fetch()
    }

    override suspend fun fetchByName(name: String): RequestState<List<Roadmap>> {
        return roadmapRemoteDataSource.fetchByName(name)
    }

}