package com.hitg.domain.usecases

import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import com.hitg.domain.exception.EmptyDescriptionException
import com.hitg.domain.exception.EmptyNameException
import com.hitg.domain.exception.InvalidLatLongException
import com.hitg.domain.repository.PointOfInterestRepository

class CreatePointOfInterestUseCase(
    private val getUserLoggedUseCase: GetUserLoggedUseCase,
    private val poiRepository: PointOfInterestRepository
) {

    suspend fun create(poi: PointOfInterest): RequestState<PointOfInterest> {
        val userLogged = getUserLoggedUseCase.getUserLogged()

        return when (userLogged) {
            is RequestState.Success -> {
                if (poi.name.isBlank()) {
                    return RequestState.Error(EmptyNameException())
                }
                if (poi.description.isBlank()) {
                    return RequestState.Error(EmptyDescriptionException())
                }
                if (poi.latitude < -90.0 && poi.latitude > 90.0) {
                    return RequestState.Error(InvalidLatLongException())
                }
                if (poi.longitude < -180.0 && poi.longitude > 180.0) {
                    return RequestState.Error(InvalidLatLongException())
                }

                val state = poiRepository.create(poi)
                return when (state) {
                    is RequestState.Success -> {
                        return state
                    }
                    is RequestState.Loading -> {
                        return RequestState.Loading
                    }
                    is RequestState.Error -> {
                        return RequestState.Error(Exception("Point of interest could not be created"))
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