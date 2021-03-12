package com.fiap.data.remote.datasource

import com.fiap.data.remote.mapper.NewUserFirebasePayloadMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hitg.domain.entity.*
import kotlinx.coroutines.tasks.await

class RoadmapRemoteFirebaseDataSourceImpl(
    private val mAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : RoadmapRemoteDataSource {


    override suspend fun create(roadmap: Roadmap): RequestState<Roadmap> {
        return try {
            firebaseFirestore.collection("roadmaps")
                .document(roadmap.creatorId)
                .set(roadmap)
                .await()
            RequestState.Success(roadmap)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

}