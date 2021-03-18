package com.hitg.domain.usecases

import com.hitg.domain.entity.RequestState
import com.hitg.domain.repository.SloganRepository

class GetSloganUseCase(
    private val sloganRepository: SloganRepository
) {
    suspend fun getSlogan(): RequestState<String> = sloganRepository.getSlogan()
}