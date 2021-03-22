package com.fiap.meurole.roadmap.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import com.hitg.domain.usecases.CreateRoadmapUseCase
import com.hitg.domain.usecases.DeletePointOfInterestUseCase
import com.hitg.domain.usecases.LogAnalyticsEventUseCase
import kotlinx.coroutines.launch

class CreateEditRoadmapViewModel(
    private val createRoadmapUseCase: CreateRoadmapUseCase,
    private val deletePointOfInterestUseCase: DeletePointOfInterestUseCase,
    private val logAnalyticsEvent: LogAnalyticsEventUseCase
) : ViewModel() {

    var saveRoadmapState = MutableLiveData<RequestState<Roadmap>>()
    var deletePointOfInterestState = MutableLiveData<RequestState<String>>()

    fun createEditRoadmap(roadmap: Roadmap) {
        viewModelScope.launch {
            val response = createRoadmapUseCase.createOrEdit(roadmap)

            when (response) {
                is RequestState.Success -> {
                    logAnalyticsEvent.logCreateRoadmap()
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