package com.fiap.data.remote.datasource

import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap

interface RoadmapRemoteDataSource {

    suspend fun createOrEdit(roadmap: Roadmap): RequestState<Roadmap>

    suspend fun fetch(): RequestState<List<Roadmap>>

    suspend fun fetchByName(name: String): RequestState<List<Roadmap>>

}