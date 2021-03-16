package com.hitg.domain.repository

import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap

interface RoadmapRepository {
    suspend fun create(roadmap: Roadmap): RequestState<String>
}