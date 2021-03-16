package com.fiap.meurole.roadmap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import com.hitg.domain.usecases.CreateRoadmapUseCase
import kotlinx.coroutines.launch

class CreateRoadmapViewModel(
    private val createRoadmapUseCase: CreateRoadmapUseCase
): ViewModel() {

    var saveRoadmapState = MutableLiveData<RequestState<Roadmap>>()

    fun createRoadmap(roadmap: Roadmap) {
        viewModelScope.launch {
            val response = createRoadmapUseCase.create(roadmap)

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

}