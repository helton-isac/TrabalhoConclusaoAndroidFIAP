package com.fiap.data.remote.datasource

import com.fiap.data.remote.mapper.NewRoadmapPayloadMapper
import com.fiap.data.remote.mapper.NewUserFirebasePayloadMapper
import com.google.firebase.firestore.FirebaseFirestore
import com.hitg.domain.entity.*
import kotlinx.coroutines.tasks.await

class RoadmapRemoteFirebaseDataSourceImpl(
    private val firebaseFirestore: FirebaseFirestore
) : RoadmapRemoteDataSource {

    override suspend fun create(roadmap: Roadmap): RequestState<String> {
        return try {
            val newRoadmapPayload = NewRoadmapPayloadMapper.mapToNewRoadmap(roadmap)
            val document = firebaseFirestore.collection("roadmaps")
                .add(newRoadmapPayload)
                .await()
            RequestState.Success(document.id)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

}