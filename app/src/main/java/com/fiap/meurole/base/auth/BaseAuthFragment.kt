package com.fiap.meurole.base.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fiap.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import com.fiap.data.repository.UserRepositoryImpl
import com.fiap.meurole.base.BaseFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hitg.domain.entity.RequestState
import com.hitg.domain.usecases.GetUserLoggedUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
abstract class BaseAuthFragment : BaseFragment() {

    private val baseAuthViewModel: BaseAuthViewModel by lazy {
        ViewModelProvider(
            this,
            BaseViewModelFactory(
                GetUserLoggedUseCase(
                    UserRepositoryImpl(
                        UserRemoteFirebaseDataSourceImpl(
                            Firebase.auth,
                            Firebase.firestore
                        )
                    )
                )
            )
        ).get(BaseAuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        registerObserver()

        baseAuthViewModel.getUserLogged()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun registerObserver() {

        baseAuthViewModel.userLoggedState.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is RequestState.Loading -> {
                    showLoading()
                }

                is RequestState.Success -> {
                    hideLoading()
                }

                is RequestState.Error -> {
                    hideLoading()

//                    findNavController().navigate(
//                        R.id.login_nav_graph, bundleOf(
//                            NAVIGATION_KEY to findNavController().currentDestination?.id
//                        )
//                    )
                }
            }
        })
    }
}