package com.hitg.domain.entity

data class Roadmap (
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var pointOfInterests: List<PointOfInterest>,
    var creatorId: String
)