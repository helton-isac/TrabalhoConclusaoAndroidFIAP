package com.fiap.meurole.about

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitg.domain.entity.RequestState
import com.hitg.domain.usecases.GetSloganUseCase
import kotlinx.coroutines.launch

class AboutViewModel(
    private val getSloganUseCase: GetSloganUseCase,
) : ViewModel() {
    val slogan = MutableLiveData<RequestState<String>>()

    fun getSlogan() {
        viewModelScope.launch {
            slogan.value = getSloganUseCase.getSlogan()
        }
    }
}