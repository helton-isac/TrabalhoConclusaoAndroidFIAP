package com.fiap.meurole.roadmapList

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.fiap.meurole.R
import com.fiap.meurole.base.auth.BaseAuthFragment
import com.fiap.meurole.utils.DialogUtils
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class RoadmapListFragment : BaseAuthFragment() {

    override val layout = R.layout.roadmap_list_fragment

    private lateinit var rvRoadmap: RecyclerView

    private val viewModel: RoadmapListViewModel by viewModel()

    private var roadmaps: MutableList<Roadmap> = arrayListOf()

    private var isFilteredList = false
    private var searchedRoadmap: String? = null

    override fun onResume() {
        super.onResume()
        if (searchedRoadmap != null) {
            setTitle(searchedRoadmap!!)
        } else {
            setTitle(getString(R.string.search_roadmaps))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()

        setUpView(view)

        searchedRoadmap = arguments?.getString("search")
        val receivedRoadmaps = arguments?.getSerializable("roadmaps")
        if (receivedRoadmaps != null) {
            isFilteredList = true
            roadmaps = receivedRoadmaps as MutableList<Roadmap>
            setUpAdapter()
        } else {
            showLoading()
            if (viewModel.roadmapState.value !is RequestState.Success) {
                viewModel.fetchRoadmaps()
            }
        }
    }

    private fun setUpView(view: View) {
        rvRoadmap = view.findViewById(R.id.rvRoadmaps)
    }

    private fun registerObserver() {
        baseAuthViewModel.userLoggedState.observe(viewLifecycleOwner, { result ->
            when (result) {
                is RequestState.Loading -> showLoading()
                is RequestState.Success -> if (isFilteredList) {
                    hideLoading()
                }
                is RequestState.Error -> if (isFilteredList) {
                    hideLoading()
                }
            }
        })

        viewModel.roadmapState.observe(viewLifecycleOwner, {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    roadmaps = it.data as MutableList<Roadmap>
                    setUpAdapter()
                }

                is RequestState.Error -> {
                    hideLoading()
                    DialogUtils.showToastErrorMessage(requireContext(), it.throwable.message)
                }
                is RequestState.Loading -> {
                    showLoading()
                }
            }
        })
        setFragmentResultListener("create_edit_roadmap") { requestKey, bundle ->
            showLoading()
            if (searchedRoadmap != null) {
                viewModel.searchRoadmaps(searchedRoadmap!!)
            } else {
                viewModel.fetchRoadmaps()
            }
        }
    }

    private fun setUpAdapter() {
        rvRoadmap.adapter =
            RoadmapAdapter(roadmaps, clickListener = { roadmap, imageResource ->
                findNavController().navigate(
                    R.id.action_roadmapList_to_detailRoadmapFragment,
                    bundleOf(
                        "roadmap" to roadmap,
                        "imageResource" to imageResource
                    )
                )
            })
    }

}
