package com.hitg.domain.entity

data class PointOfInterest(
    var id: String = "",
    var latitude: Double,
    var longitude: Double,
    var name: String = "",
    var description: String = "",
    var roadmapId: String = ""
)
