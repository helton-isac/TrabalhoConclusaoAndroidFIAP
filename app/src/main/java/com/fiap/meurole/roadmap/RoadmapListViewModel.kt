package com.fiap.meurole.roadmap

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

}