package com.hitg.domain.usecases

import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import com.hitg.domain.exception.EmptyDescriptionException
import com.hitg.domain.exception.EmptyNameException
import com.hitg.domain.exception.EmptyTelephoneException
import com.hitg.domain.exception.InvalidLatLongException
import com.hitg.domain.repository.PointOfInterestRepository

class CreatePointOfInterestUseCase(
    private val poiRepository: PointOfInterestRepository
) {

    suspend fun create(poi: PointOfInterest): RequestState<PointOfInterest> {
        if (poi.name.isBlank()) {
            return RequestState.Error(EmptyNameException())
        }
        if (poi.description.isBlank()) {
            return RequestState.Error(EmptyDescriptionException())
        }
        if (poi.telephone.isBlank()) {
            return RequestState.Error(EmptyTelephoneException())
        }
        if (poi.latitude < -90.0 && poi.latitude > 90.0) {
            return RequestState.Error(InvalidLatLongException())
        }
        if (poi.longitude < -180.0 && poi.longitude > 180.0) {
            return RequestState.Error(InvalidLatLongException())
        }

        return when (val state = poiRepository.create(poi)) {
            is RequestState.Success -> {
                state
            }
            is RequestState.Loading -> {
                RequestState.Loading
            }
            is RequestState.Error -> {
                RequestState.Error(Exception("Point of interest could not be created"))
            }
        }
    }

}