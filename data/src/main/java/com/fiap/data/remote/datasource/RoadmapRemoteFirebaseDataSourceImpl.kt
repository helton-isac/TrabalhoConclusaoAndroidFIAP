package com.fiap.data.remote.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.hitg.domain.entity.*
import kotlinx.coroutines.tasks.await

class RoadmapRemoteFirebaseDataSourceImpl(
    private val firebaseFirestore: FirebaseFirestore
) : RoadmapRemoteDataSource {

    override suspend fun create(roadmap: Roadmap): RequestState<Roadmap> {
        return try {
            firebaseFirestore.collection("roadmaps")
                .add(roadmap)
                .await()
            RequestState.Success(roadmap)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

}