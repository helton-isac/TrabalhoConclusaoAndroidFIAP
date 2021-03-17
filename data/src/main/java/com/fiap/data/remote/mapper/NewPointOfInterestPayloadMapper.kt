package com.fiap.data.remote.mapper

import com.fiap.data.remote.models.NewPointOfInterestPayload
import com.hitg.domain.entity.PointOfInterest

object NewPointOfInterestPayloadMapper {

    fun mapToNewPointOfInterest(poi: PointOfInterest) = NewPointOfInterestPayload(
        name = poi.name,
        description = poi.description,
        latitude = poi.latitude,
        longitude = poi.longitude,
        roadmapId = poi.roadmapId
    )

}