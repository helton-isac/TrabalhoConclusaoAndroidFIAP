package com.fiap.meurole.roadmap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import com.hitg.domain.usecases.CreateRoadmapUseCase

class CreateRoadmapViewModel(
    private val createRoadmapUseCase: CreateRoadmapUseCase
): ViewModel() {

    var saveRoadmapState = MutableLiveData<RequestState<Roadmap>>()

    suspend fun createRoadmap(roadmap: Roadmap) {
        val response = createRoadmapUseCase.create(roadmap)

        when (response) {
            is RequestState.Success -> {
                saveRoadmapState.value = RequestState.Success(roadmap)
            }
            is RequestState.Error -> {
                saveRoadmapState.value = RequestState.Error(Exception("Roadmap save failed."))
            }
            is RequestState.Loading -> {
                saveRoadmapState.value = RequestState.Loading
            }
        }
    }

}