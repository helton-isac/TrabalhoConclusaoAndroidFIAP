package com.fiap.meurole.home

import androidx.lifecycle.ViewModelProvider
import com.fiap.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import com.fiap.data.repository.UserRepositoryImpl
import com.fiap.meurole.R
import com.fiap.meurole.base.auth.BaseAuthFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hitg.domain.usecases.GetUserLoggedUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HomeFragment : BaseAuthFragment() {

    override val layout = R.layout.home_fragment

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(
            this,
            HomeViewModelFactory(
                GetUserLoggedUseCase(
                    UserRepositoryImpl(
                        UserRemoteFirebaseDataSourceImpl(
                            Firebase.auth,
                            Firebase.firestore
                        )
                    )
                )
            )
        ).get(HomeViewModel::class.java)
    }
}