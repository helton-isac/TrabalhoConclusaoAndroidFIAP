package com.fiap.meurole.home

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fiap.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import com.fiap.data.repository.UserRepositoryImpl
import com.fiap.meurole.R
import com.fiap.meurole.base.auth.BaseAuthFragment
import com.fiap.meurole.base.auth.NAVIGATION_KEY
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hitg.domain.usecases.GetUserLoggedUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class HomeFragment : BaseAuthFragment() {

    override val layout = R.layout.home_fragment

    private lateinit var bnvHome: BottomNavigationView

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()

        setUpView(view)
    }

    private fun setUpView(view: View) {
        bnvHome = view.findViewById(R.id.bnvHome)
        bnvHome.selectedItemId = R.id.navigation_home
        bnvHome.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    true
                }
                R.id.navigation_profile -> {
                    findNavController().navigate(
                        R.id.profileFragment, bundleOf(
                            NAVIGATION_KEY to findNavController().currentDestination?.id
                        )
                    )
                    true
                }
                else -> false
            }
        }
    }

    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}