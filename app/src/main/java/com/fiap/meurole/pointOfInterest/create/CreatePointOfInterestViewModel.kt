package com.fiap.meurole.pointOfInterest.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import com.hitg.domain.usecases.CreatePointOfInterestUseCase
import kotlinx.coroutines.launch

class CreatePointOfInterestViewModel(
    private val createPointOfInterestUseCase: CreatePointOfInterestUseCase
): ViewModel() {

    var poiState = MutableLiveData<RequestState<PointOfInterest>>()

    fun create(poi: PointOfInterest) {
        viewModelScope.launch {

            when (val response = createPointOfInterestUseCase.create(poi)) {
                is RequestState.Success -> {
                    poiState.value = RequestState.Success(poi)
                }
                is RequestState.Error -> {
                    poiState.value = response
                }
                is RequestState.Loading -> {
                    poiState.value = RequestState.Loading
                }
            }
        }
    }

}