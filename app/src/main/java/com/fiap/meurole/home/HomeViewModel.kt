package com.fiap.meurole.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import com.hitg.domain.usecases.FetchRoadmapsUseCase
import com.hitg.domain.usecases.GetUserLoggedUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getUserLoggedUseCase: GetUserLoggedUseCase,
    private val fetchRoadmapsUseCase: FetchRoadmapsUseCase
) : ViewModel() {

    var roadmapState = MutableLiveData<RequestState<List<Roadmap>>>()

    fun searchRoadmaps(name: String) {
        viewModelScope.launch {
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

    fun clearState() {
        roadmapState.value = RequestState.Success(listOf(Roadmap(id = "0")))
    }

}
