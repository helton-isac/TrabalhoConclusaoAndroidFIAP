package com.fiap.data.remote.models

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class NewPointOfInterestPayload(
    val name: String? = null,
    val description: String? = null,
    val telephone: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val roadmapId: String? = null
)