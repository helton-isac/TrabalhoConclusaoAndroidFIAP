package com.fiap.meurole.profile

import androidx.lifecycle.ViewModel
import com.hitg.domain.usecases.GetUserLoggedUseCase

class ProfileViewModel(
    private val getUserLoggedUseCase: GetUserLoggedUseCase
) : ViewModel() {
}