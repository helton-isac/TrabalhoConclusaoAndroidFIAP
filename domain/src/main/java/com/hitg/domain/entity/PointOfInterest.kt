package com.hitg.domain.entity

import java.io.Serializable

data class PointOfInterest(
    var id: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var name: String = "",
    var description: String = "",
    var telephone: String = ""
): Serializable
