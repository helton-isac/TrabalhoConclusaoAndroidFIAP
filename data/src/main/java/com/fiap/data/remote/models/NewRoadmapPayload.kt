package com.fiap.data.remote.models

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class NewRoadmapPayload(
    val name: String? = null,
    val description: String? = null,
    val creatorId: String? = null
)