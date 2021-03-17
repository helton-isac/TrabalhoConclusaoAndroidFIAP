package com.fiap.meurole.roadmap

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.fiap.meurole.R
import com.fiap.meurole.base.BaseFragment
import com.fiap.meurole.base.auth.NAVIGATION_KEY
import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class RoadmapListFragment : BaseFragment() {

    override val layout = R.layout.roadmap_list_fragment

    private lateinit var rvRoadmap: RecyclerView

    private val viewModel: RoadmapListViewModel by viewModel()

    private var roadmaps: MutableList<Roadmap> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()

        registerObserver()

        setUpView(view)

        showLoading()
        viewModel.fetchRoadmaps()
    }

    private fun setUpView(view: View) {
        rvRoadmap = view.findViewById(R.id.rvRoadmaps)
    }

    private fun registerBackPressedAction() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun registerObserver() {
        viewModel.roadmapState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()

                    roadmaps = it.data as MutableList<Roadmap>
                    rvRoadmap.adapter = RoadmapAdapter(roadmaps)
                }
                is RequestState.Error -> {
                    hideLoading()
                    showMessage(it.throwable.message)
                }
                is RequestState.Loading -> {
                    showLoading()
                }
            }
        })
    }

}
