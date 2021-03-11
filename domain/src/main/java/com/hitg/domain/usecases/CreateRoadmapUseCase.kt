package com.hitg.domain.usecases

import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import com.hitg.domain.exception.EmptyDescriptionException
import com.hitg.domain.exception.EmptyNameException
import com.hitg.domain.exception.EmptyPointOfInterestException
import com.hitg.domain.exception.InvalidLatLongException
import com.hitg.domain.repository.RoadmapRepository

class CreateRoadmapUseCase (private val roadmapRepository: RoadmapRepository) {

    suspend fun create(roadmap: Roadmap): RequestState<Roadmap> {

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
            if (point.latitude > -90 && point.latitude < 90) {
                return RequestState.Error(InvalidLatLongException())
            }
            if (point.longitude > -180 && point.longitude < 180) {
                return RequestState.Error(InvalidLatLongException())
            }
        }

        return roadmapRepository.create(roadmap)

    }

}