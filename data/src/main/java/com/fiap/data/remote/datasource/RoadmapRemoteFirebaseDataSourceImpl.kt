package com.fiap.data.remote.datasource

import com.fiap.data.remote.mapper.NewRoadmapPayloadMapper
import com.google.firebase.firestore.FirebaseFirestore
import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import kotlinx.coroutines.tasks.await

class RoadmapRemoteFirebaseDataSourceImpl(
    private val firebaseFirestore: FirebaseFirestore
) : RoadmapRemoteDataSource {

    override suspend fun create(roadmap: Roadmap): RequestState<Roadmap> {
        return try {
            val newRoadmapPayload = NewRoadmapPayloadMapper.mapToNewRoadmap(roadmap)
            val document = firebaseFirestore.collection("roadmaps")
                .add(newRoadmapPayload)
                .await()
            roadmap.id = document.id
            RequestState.Success(roadmap)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun fetch(): RequestState<List<Roadmap>> {
        return try {
            val result = firebaseFirestore.collection("roadmaps")
                .get()
                .await()
                .documents

            val roadmaps = result.map {
                val poiIds: List<String> = it.get ("pointOfInterests") as List<String>
                val roadmap = Roadmap(
                    id = it.id,
                    name = it.getString("name") ?: "",
                    description = it.getString("description") ?: "",
                    pointOfInterests = poiIds.map { id -> PointOfInterest(id = id) },
                    creatorId = it.getString("creatorId") ?: "",
                    creatorName = it.getString("creatorName") ?: ""
                )
                roadmap
            }

            if (roadmaps.isEmpty()) {
                RequestState.Error(Exception("No roadmap found"))
            } else {
                RequestState.Success(roadmaps)
            }
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun fetchByName(name: String): RequestState<List<Roadmap>> {
        return try {
            val result = firebaseFirestore.collection("roadmaps")
                .whereEqualTo("name", name)
                .get()
                .await()
                .documents

            val roadmaps = result.map {
                val poiIds: List<String> = it.get ("pointOfInterests") as List<String>
                val roadmap = Roadmap(
                    id = it.id,
                    name = it.getString("name") ?: "",
                    description = it.getString("description") ?: "",
                    pointOfInterests = poiIds.map { id -> PointOfInterest(id = id) },
                    creatorId = it.getString("creatorId") ?: "",
                    creatorName = it.getString("creatorName") ?: ""
                )
                roadmap
            }

            RequestState.Success(roadmaps)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

}