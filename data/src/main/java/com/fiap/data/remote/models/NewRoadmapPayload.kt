package com.fiap.data.remote.models

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class NewRoadmapPayload(
    val name: String? = null,
    val description: String? = null,
    val pointOfInterests: List<String> = arrayListOf(),
    val creatorId: String? = null,
    val creatorName: String? = null
)