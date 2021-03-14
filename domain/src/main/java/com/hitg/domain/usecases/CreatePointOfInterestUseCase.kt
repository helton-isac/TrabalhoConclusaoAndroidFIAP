package com.hitg.domain.usecases

import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import com.hitg.domain.exception.EmptyDescriptionException
import com.hitg.domain.exception.EmptyNameException
import com.hitg.domain.repository.PointOfInterestRepository
import com.hitg.domain.exception.InvalidLatLongException as InvalidLatLongException1

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
        if (poi.latitude > -90 && poi.latitude < 90) {
            return RequestState.Error(InvalidLatLongException1())
        }
        if (poi.longitude > -180 && poi.longitude < 180) {
            return RequestState.Error(InvalidLatLongException1())
        }

        return poiRepository.create(poi)
    }

}