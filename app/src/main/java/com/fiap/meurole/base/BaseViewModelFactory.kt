package com.fiap.meurole.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hitg.domain.usecases.GetUserLoggedUseCase

class BaseViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GetUserLoggedUseCase::class.java).newInstance()
    }
}