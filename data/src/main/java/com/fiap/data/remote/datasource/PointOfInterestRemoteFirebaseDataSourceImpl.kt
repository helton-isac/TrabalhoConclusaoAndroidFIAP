package com.fiap.data.remote.datasource

import com.fiap.data.remote.mapper.NewPointOfInterestPayloadMapper
import com.google.firebase.firestore.FirebaseFirestore
import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
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

    override suspend fun fetch(roadmapId: String): RequestState<List<PointOfInterest>> {
        return try {
            val result = firebaseFirestore.collection("pointOfInterest")
                .whereEqualTo("roadmapId", roadmapId)
                .get()
                .await()
                .documents

            val pois = result.map {
                val poi = PointOfInterest(
                    id = it.id,
                    name = it.getString("name") ?: "",
                    description = it.getString("description") ?: "",
                    latitude = it.getDouble("latitude") ?: 0.0,
                    longitude = it.getDouble("longitude") ?: 0.0,
                    roadmapId = it.getString("roadmapId") ?: "",
                )
                poi
            }

            if (pois == null || pois.isEmpty()) {
                RequestState.Error(Exception("Point of interest not found"))
            } else {
                RequestState.Success(pois)
            }

        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }


}