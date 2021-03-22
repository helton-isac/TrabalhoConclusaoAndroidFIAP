package com.hitg.domain.repository

import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap

interface RoadmapRepository {
    suspend fun createOrEdit(roadmap: Roadmap): RequestState<Roadmap>

    suspend fun fetch(): RequestState<List<Roadmap>>

    suspend fun fetchByName(name: String): RequestState<List<Roadmap>>
}