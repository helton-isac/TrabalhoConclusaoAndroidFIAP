package com.hitg.domain.entity

import java.io.Serializable

data class PointOfInterest(
    var latitude: Double,
    var longitude: Double,
    var name: String = "",
    var description: String = "",
    var roadmapId: String = ""
): Serializable
