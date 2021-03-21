package com.fiap.meurole.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.fiap.meurole.R
import com.fiap.meurole.base.auth.BaseAuthFragment
import com.fiap.meurole.roadmapList.RoadmapAdapter
import com.fiap.meurole.utils.DialogUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputLayout
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class HomeFragment : BaseAuthFragment() {

    override val layout = R.layout.home_fragment

    private lateinit var etSearchRoadmap: EditText
    private lateinit var btRoadmaps: Button
    private lateinit var btSearchMap: Button
    private lateinit var btCreateRoadmap: Button
    private lateinit var bnvHome: BottomNavigationView

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        setDisplayHomeAsUpEnabled(false)
        setTitle(getString(R.string.app_name))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu_action_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()

        setUpView(view)
        registerObserver()
    }

    private fun setUpView(view: View) {
        etSearchRoadmap = view.findViewById(R.id.tilSearchRoadmap)
        btRoadmaps = view.findViewById(R.id.btRoadmaps)
        btSearchMap = view.findViewById(R.id.btSearchMap)
        btCreateRoadmap = view.findViewById(R.id.btCreateRoadmap)

        btRoadmaps.setOnClickListener {
            if (etSearchRoadmap.text.isNotBlank()) {
                showLoading()
                homeViewModel.searchRoadmaps(etSearchRoadmap.text.toString())
            } else {
                findNavController().navigate(R.id.action_homeFragment_to_roadmapList)
            }
        }

        btSearchMap.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mapFragment)
        }

        btCreateRoadmap.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createRoadmapFragment)
        }

        bnvHome = view.findViewById(R.id.bnvHome)
        bnvHome.selectedItemId = R.id.navigation_home
        bnvHome.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    true
                }
                R.id.navigation_profile -> {
                    findNavController().navigate(R.id.profileFragment)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_action_menu_about -> {
                findNavController().navigate(R.id.action_homeFragment_to_aboutFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerObserver() {
        baseAuthViewModel.userLoggedState.observe(viewLifecycleOwner, { result ->
            when (result) {
                is RequestState.Loading -> showLoading()
                is RequestState.Success -> hideLoading()
                is RequestState.Error -> hideLoading()
            }
        })

        homeViewModel.roadmapState.observe(viewLifecycleOwner, { result ->
            when (result) {
                is RequestState.Loading -> showLoading()

                is RequestState.Success -> {
                    hideLoading()
                    val roadmaps = result.data as MutableList<Roadmap>

                    findNavController().navigate(R.id.action_homeFragment_to_roadmapList,
                        bundleOf(
                            "roadmaps" to roadmaps
                        )
                    )
                }

                is RequestState.Error -> {
                    hideLoading()
                    DialogUtils.showToastErrorMessage(requireContext(), result.throwable.message)
                }
            }
        })
    }
}