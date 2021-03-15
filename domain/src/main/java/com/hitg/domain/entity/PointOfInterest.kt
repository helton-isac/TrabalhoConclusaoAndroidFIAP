package com.hitg.domain.entity

import kotlinx.android.parcel.Parcelize

@Parcelize
data class PointOfInterest(
    var id: String = "",
    var latitude: Double,
    var longitude: Double,
    var name: String = "",
    var description: String = "",
    var roadmapId: String = ""
): android.os.Parcelable
