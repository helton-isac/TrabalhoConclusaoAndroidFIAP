package com.fiap.meurole.roadmapList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import com.hitg.domain.usecases.FetchRoadmapsUseCase
import kotlinx.coroutines.launch

class RoadmapListViewModel(
    private val fetchRoadmapsUseCase: FetchRoadmapsUseCase
): ViewModel() {

    var roadmapState = MutableLiveData<RequestState<List<Roadmap>>>()

    fun fetchRoadmaps() {
        viewModelScope.launch {
            roadmapState.value = RequestState.Loading
            val response = fetchRoadmapsUseCase.fetch()

            when (response) {
                is RequestState.Success -> {
                    roadmapState.value = response
                }
                is RequestState.Error -> {
                    roadmapState.value = response
                }
                is RequestState.Loading -> {
                    roadmapState.value = RequestState.Loading
                }
            }
        }
    }

    fun searchRoadmaps(name: String) {
        viewModelScope.launch {
            roadmapState.value = RequestState.Loading
            val response = fetchRoadmapsUseCase.fetchByName(name)

            when (response) {
                is RequestState.Success -> {
                    roadmapState.value = response
                }
                is RequestState.Error -> {
                    roadmapState.value = response
                }
                is RequestState.Loading -> {
                    roadmapState.value = RequestState.Loading
                }
            }
        }
    }

}