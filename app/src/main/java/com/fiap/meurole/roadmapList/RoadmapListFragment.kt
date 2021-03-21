package com.fiap.meurole.roadmapList

import android.os.Bundle
import android.view.View
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

    override fun onResume() {
        super.onResume()
        setTitle(getString(R.string.search_roadmaps))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()

        setUpView(view)

        val receivedRoadmaps = arguments?.getSerializable("roadmaps")
        if (receivedRoadmaps != null) {
            roadmaps = receivedRoadmaps as MutableList<Roadmap>
            rvRoadmap.adapter = RoadmapAdapter(roadmaps)
            hideLoading()
        } else {
            showLoading()
            viewModel.fetchRoadmaps()
        }
    }

    private fun setUpView(view: View) {
        rvRoadmap = view.findViewById(R.id.rvRoadmaps)
    }

    private fun registerObserver() {
        viewModel.roadmapState.observe(viewLifecycleOwner, {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    roadmaps = it.data as MutableList<Roadmap>
                    rvRoadmap.adapter = RoadmapAdapter(roadmaps)
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
    }

}
