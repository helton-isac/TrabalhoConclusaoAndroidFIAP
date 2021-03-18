package com.fiap.data.remote.datasource

import com.fiap.data.remote.mapper.NewPointOfInterestPayloadMapper
import com.google.firebase.firestore.FieldPath
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
            val document = firebaseFirestore.collection("pointOfInterest")
                .add(payload)
                .await()
            poi.id = document.id
            RequestState.Success(poi)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun fetch(idList: List<String>): RequestState<List<PointOfInterest>> {
        return try {
            val result = firebaseFirestore.collection("pointOfInterest")
                .whereIn(FieldPath.documentId(), idList)
                .get()
                .await()
                .documents

            val pois = result.map {
                val poi = PointOfInterest(
                    id = it.id,
                    name = it.getString("name") ?: "",
                    description = it.getString("description") ?: "",
                    latitude = it.getDouble("latitude") ?: 0.0,
                    longitude = it.getDouble("longitude") ?: 0.0
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

    override suspend fun delete(id: String): RequestState<String> {
        return try {
            firebaseFirestore.collection("pointOfInterest")
                .document(id)
                .delete()
                .await()
            RequestState.Success(id)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun edit(poi: PointOfInterest): RequestState<PointOfInterest> {
        return try {
            val payload = NewPointOfInterestPayloadMapper.mapToNewPointOfInterest(poi)
            firebaseFirestore.collection("pointOfInterest")
                .document(poi.id)
                .set(payload)
                .await()
            RequestState.Success(poi)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }


}