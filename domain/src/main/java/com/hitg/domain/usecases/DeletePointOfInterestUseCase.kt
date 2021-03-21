package com.hitg.domain.usecases

import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import com.hitg.domain.repository.PointOfInterestRepository

class DeletePointOfInterestUseCase(
    private val poiRepository: PointOfInterestRepository
) {

    suspend fun delete(poi: PointOfInterest): RequestState<String> {

        if (poi.id.isBlank()) {
            return RequestState.Error(Exception("Missing Point of Interest ID."))
        }

        return when (val state = poiRepository.delete(poi.id)) {
            is RequestState.Success -> {
                state
            }
            is RequestState.Loading -> {
                RequestState.Loading
            }
            is RequestState.Error -> {
                RequestState.Error(Exception("Point of interest could not be deleted"))
            }
        }
    }

}