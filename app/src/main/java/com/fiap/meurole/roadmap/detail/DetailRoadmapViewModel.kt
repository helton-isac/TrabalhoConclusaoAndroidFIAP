package com.fiap.meurole.roadmap.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import com.hitg.domain.usecases.CreateRoadmapUseCase
import com.hitg.domain.usecases.DeletePointOfInterestUseCase
import kotlinx.coroutines.launch

class DetailRoadmapViewModel(
    private val createRoadmapUseCase: CreateRoadmapUseCase,
    private val deletePointOfInterestUseCase: DeletePointOfInterestUseCase
) : ViewModel() {

    var saveRoadmapState = MutableLiveData<RequestState<Roadmap>>()
    var deletePointOfInterestState = MutableLiveData<RequestState<String>>()

    fun createRoadmap(roadmap: Roadmap) {
        viewModelScope.launch {
            val response = createRoadmapUseCase.createOrEdit(roadmap)

            when (response) {
                is RequestState.Success -> {
                    saveRoadmapState.value = RequestState.Success(roadmap)
                }
                is RequestState.Error -> {
                    saveRoadmapState.value = response
                }
                is RequestState.Loading -> {
                    saveRoadmapState.value = RequestState.Loading
                }
            }
        }
    }

    fun deletePointOfInterest(poi: PointOfInterest) {
        viewModelScope.launch {
            val response = deletePointOfInterestUseCase.delete(poi)

            when (response) {
                is RequestState.Success -> {
                    deletePointOfInterestState.value = RequestState.Success(response.data)
                }
                is RequestState.Error -> {
                    deletePointOfInterestState.value = response
                }
                is RequestState.Loading -> {
                    deletePointOfInterestState.value = RequestState.Loading
                }
            }
        }
    }

}