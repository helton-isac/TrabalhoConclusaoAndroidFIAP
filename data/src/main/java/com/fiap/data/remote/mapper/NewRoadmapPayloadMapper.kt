package com.fiap.data.remote.mapper

import com.fiap.data.remote.models.NewRoadmapPayload
import com.hitg.domain.entity.Roadmap

object NewRoadmapPayloadMapper {

    fun mapToNewRoadmap(roadmap: Roadmap) = NewRoadmapPayload(
        name = roadmap.name,
        description = roadmap.description,
        creatorId = roadmap.creatorId
    )

}