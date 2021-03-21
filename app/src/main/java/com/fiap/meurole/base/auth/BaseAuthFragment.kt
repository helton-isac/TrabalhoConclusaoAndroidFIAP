package com.fiap.meurole.base.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.fiap.meurole.R
import com.fiap.meurole.base.BaseFragment
import com.hitg.domain.entity.RequestState.*
import com.hitg.domain.entity.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

const val NAVIGATION_KEY = "NAV_KEY"

@ExperimentalCoroutinesApi
abstract class BaseAuthFragment : BaseFragment() {

    protected val baseAuthViewModel: BaseAuthViewModel by viewModel()

    protected var userLogged: User = User()

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
        baseAuthViewModel.userLoggedState.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Loading -> showLoading()
                is Success -> {
                    userLogged = result.data
                }
                is Error -> {
                    hideLoading()
                    findNavController().navigate(
                        R.id.login_nav_graph, bundleOf(
                            NAVIGATION_KEY to findNavController().currentDestination?.id
                        )
                    )
                }
            }
        })
    }
}