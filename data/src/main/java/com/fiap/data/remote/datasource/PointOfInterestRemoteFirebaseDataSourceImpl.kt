package com.fiap.data.remote.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import kotlinx.coroutines.tasks.await

class PointOfInterestRemoteFirebaseDataSourceImpl(
    private val firebaseFirestore: FirebaseFirestore
) : PointOfInterestRemoteDataSource {

    override suspend fun create(poi: PointOfInterest): RequestState<PointOfInterest> {
        return try {
            firebaseFirestore.collection("pointOfInterest")
                .add(poi)
                .await()
            RequestState.Success(poi)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

}