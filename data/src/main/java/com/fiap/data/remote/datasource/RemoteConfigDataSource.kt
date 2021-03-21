package com.fiap.data.remote.datasource

import com.hitg.domain.entity.RequestState

interface RemoteConfigDataSource {

    suspend fun getSlogan(): RequestState<String>

    suspend fun isGoogleSignInEnabled(): RequestState<Boolean>

    suspend fun isFacebookSignInEnabled(): RequestState<Boolean>
}
