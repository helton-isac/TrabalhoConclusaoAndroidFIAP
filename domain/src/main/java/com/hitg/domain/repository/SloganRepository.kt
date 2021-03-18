package com.hitg.domain.repository

import com.hitg.domain.entity.RequestState

interface SloganRepository {
    suspend fun getSlogan(): RequestState<String>
}