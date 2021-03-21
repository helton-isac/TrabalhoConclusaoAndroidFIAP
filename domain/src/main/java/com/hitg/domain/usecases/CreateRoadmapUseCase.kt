package com.hitg.domain.usecases

import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import com.hitg.domain.exception.EmptyNameException
import com.hitg.domain.exception.EmptyPointOfInterestException
import com.hitg.domain.repository.RoadmapRepository

class CreateRoadmapUseCase(
    private val getUserLoggedUseCase: GetUserLoggedUseCase,
    private val roadmapRepository: RoadmapRepository
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

                roadmap.creatorId = userLogged.data.id

                return when (roadmapRepository.create(roadmap)) {
                    is RequestState.Success -> {
                        RequestState.Success(roadmap)
                    }
                    is RequestState.Loading -> {
                        RequestState.Loading
                    }
                    is RequestState.Error -> {
                        RequestState.Error(Exception("Erro ao criar o roteiro"))
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