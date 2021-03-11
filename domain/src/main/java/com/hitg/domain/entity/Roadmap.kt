package com.hitg.domain.entity

data class Roadmap (
    var id: String = "",
    var name: String = "",
    var pointOfInterests: Array<PointOfInterest>,
    var creator: User
)