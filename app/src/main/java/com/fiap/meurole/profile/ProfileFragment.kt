package com.fiap.meurole.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.fiap.meurole.R
import com.fiap.meurole.base.auth.BaseAuthFragment
import com.fiap.meurole.base.auth.NAVIGATION_KEY
import com.fiap.meurole.utils.DialogUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hitg.domain.entity.RequestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class ProfileFragment : BaseAuthFragment() {


    override val layout = R.layout.profile_fragment

    private lateinit var bnvProfile: BottomNavigationView
    private lateinit var tvUserName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var bvSignOut: Button

    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        setDisplayHomeAsUpEnabled(false)
        setTitle(getString(R.string.profile_title))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu_action_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()

        setUpView(view)

        registerObserver()

        profileViewModel.getUserLogged()
    }

    private fun setUpView(view: View) {
        bnvProfile = view.findViewById(R.id.bnvHome)
        tvUserName = view.findViewById(R.id.tvUserName)
        tvEmail = view.findViewById(R.id.tvEmail)
        bvSignOut = view.findViewById(R.id.bvSignOut)

        bnvProfile.selectedItemId = R.id.navigation_profile
        bnvProfile.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    findNavController().navigate(
                        R.id.homeFragment, bundleOf(
                            NAVIGATION_KEY to findNavController().currentDestination?.id
                        )
                    )
                    true
                }
                R.id.navigation_profile -> {
                    true
                }
                else -> false
            }
        }

        bvSignOut.setOnClickListener {
            profileViewModel.doLogout()
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

    private fun registerObserver() {
        profileViewModel.userLoggedState.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Loading -> {
                    showLoading()
                }

                is RequestState.Success -> {
                    hideLoading()
                    it.data.name
                }

                is RequestState.Error -> {
                    hideLoading()
                    DialogUtils.showToastErrorMessage(requireContext(), it.throwable.message)
                }
            }
        }

        profileViewModel.logoutResponse.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Success -> {
                    baseAuthViewModel.getUserLogged()
                }
                is RequestState.Loading -> {
                    showLoading()
                }
                is RequestState.Error -> {
                    DialogUtils.showToastErrorMessage(requireContext(), it.throwable.message)
                }
            }
        }
        baseAuthViewModel.userLoggedState.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Error -> {
                    tvUserName.text = getString(R.string.ellipsis)
                }
                is RequestState.Loading -> {
                    tvUserName.text = getString(R.string.loading)
                }
                is RequestState.Success -> {
                    tvUserName.text = it.data.name
                    tvEmail.text = it.data.email
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile_action_menu_about -> {
                findNavController().navigate(R.id.action_profileFragment_to_aboutFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}