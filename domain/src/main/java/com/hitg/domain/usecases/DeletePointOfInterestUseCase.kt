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

        val state = poiRepository.delete(poi.id)
        return when (state) {
            is RequestState.Success -> {
                return state
            }
            is RequestState.Loading -> {
                return RequestState.Loading
            }
            is RequestState.Error -> {
                return RequestState.Error(Exception("Point of interest could not be deleted"))
            }
        }
    }

}