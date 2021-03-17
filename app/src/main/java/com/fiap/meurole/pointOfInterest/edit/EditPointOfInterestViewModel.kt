package com.fiap.meurole.pointOfInterest.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import com.hitg.domain.usecases.CreatePointOfInterestUseCase
import com.hitg.domain.usecases.EditPointOfInterestUseCase
import kotlinx.coroutines.launch

class EditPointOfInterestViewModel(
    private val editPointOfInterestUseCase: EditPointOfInterestUseCase
): ViewModel() {

    var poiState = MutableLiveData<RequestState<PointOfInterest>>()

    fun edit(poi: PointOfInterest) {
        viewModelScope.launch {
            val response = editPointOfInterestUseCase.edit(poi)

            when (response) {
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