package com.fiap.data.remote.datasource

import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap

interface RoadmapRemoteDataSource {

    suspend fun create(roadmap: Roadmap): RequestState<Roadmap>

}