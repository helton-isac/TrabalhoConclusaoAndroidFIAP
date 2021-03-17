package com.hitg.domain.usecases

import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import com.hitg.domain.repository.PointOfInterestRepository
import com.hitg.domain.repository.RoadmapRepository

class FetchRoadmapsUseCase(
    private val roadmapRepository: RoadmapRepository,
    private val poiRepository: PointOfInterestRepository
) {

    suspend fun fetch(): RequestState<List<Roadmap>> {
        val roadmaps = roadmapRepository.fetch()
        return when (roadmaps) {
            is RequestState.Success -> {
                val roadmapsPoi: List<Roadmap> = roadmaps.data.map {
                    val pointOfInterests = poiRepository.fetch(it.id)
                    when (pointOfInterests) {
                        is RequestState.Success -> {
                            it.pointOfInterests = pointOfInterests.data
                            it
                        }
                        is RequestState.Loading -> {
                            return RequestState.Loading
                        }
                        is RequestState.Error -> {
                            return RequestState.Error(Exception("Falha ao buscar pontos de interesse."))
                        }
                    }
                }
                return RequestState.Success(roadmapsPoi)
            }
            is RequestState.Loading -> {
                return RequestState.Loading
            }
            is RequestState.Error -> {
                return RequestState.Error(Exception("Falha ao buscar roteiros."))
            }
        }
    }

}