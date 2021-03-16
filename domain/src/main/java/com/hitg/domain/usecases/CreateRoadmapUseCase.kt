package com.hitg.domain.usecases

import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import com.hitg.domain.exception.EmptyDescriptionException
import com.hitg.domain.exception.EmptyNameException
import com.hitg.domain.exception.EmptyPointOfInterestException
import com.hitg.domain.exception.InvalidLatLongException
import com.hitg.domain.repository.PointOfInterestRepository
import com.hitg.domain.repository.RoadmapRepository

class CreateRoadmapUseCase(
    private val getUserLoggedUseCase: GetUserLoggedUseCase,
    private val roadmapRepository: RoadmapRepository,
    private val poiRepository: PointOfInterestRepository
) {

    suspend fun create(roadmap: Roadmap): RequestState<Roadmap> {
        val userLogged = getUserLoggedUseCase.getUserLogged()

        return when (userLogged) {
            is RequestState.Success -> {
                if (roadmap.name.isBlank()) {
                    return RequestState.Error(EmptyNameException())
                }

                if (roadmap.pointOfInterests.isEmpty()) {
                    return RequestState.Error(EmptyPointOfInterestException())
                }

                for (point in roadmap.pointOfInterests) {
                    if (point.description.isBlank()) {
                        return RequestState.Error(EmptyDescriptionException())
                    }
                    if (point.latitude < -90.0 && point.latitude > 90.0) {
                        return RequestState.Error(InvalidLatLongException())
                    }
                    if (point.longitude < -180.0 && point.longitude > 180.0) {
                        return RequestState.Error(InvalidLatLongException())
                    }
                }
                roadmap.creatorId = userLogged.data.id

                val state = roadmapRepository.create(roadmap)
                return when (state) {
                    is RequestState.Success -> {
                        for (poi in roadmap.pointOfInterests) {
                            poi.roadmapId = state.data
                            poiRepository.create(poi)
                        }
                        return RequestState.Success(roadmap)
                    }
                    is RequestState.Loading -> {
                        return RequestState.Loading
                    }
                    is RequestState.Error -> {
                        return RequestState.Error(Exception("Erro ao criar o roteiro"))
                    }
                }
            }

            is RequestState.Loading -> {
                return RequestState.Loading
            }

            is RequestState.Error -> {
                return RequestState.Error(Exception("Usuário não encontrado para associar o roteiro"))
            }
        }

    }

}