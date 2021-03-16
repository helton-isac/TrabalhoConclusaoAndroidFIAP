package com.fiap.data.remote.datasource

import com.fiap.data.remote.mapper.NewPointOfInterestPayloadMapper
import com.google.firebase.firestore.FirebaseFirestore
import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import kotlinx.coroutines.tasks.await

class PointOfInterestRemoteFirebaseDataSourceImpl(
    private val firebaseFirestore: FirebaseFirestore
) : PointOfInterestRemoteDataSource {

    override suspend fun create(poi: PointOfInterest): RequestState<PointOfInterest> {
        return try {
            val payload = NewPointOfInterestPayloadMapper.mapToNewPointOfInterest(poi)
            firebaseFirestore.collection("pointOfInterest")
                .add(payload)
                .await()
            RequestState.Success(poi)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

}