package com.fiap.meurole.roadmap

import android.os.Bundle
import android.view.View
import android.widget.Button
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class CreateRoadmapFragment: BaseFragment() {

    override val layout = R.layout.create_roadmap_fragment

    private lateinit var rvPointOfInterest: RecyclerView
    private lateinit var btCreatePoi: Button

    private val createRoadmapViewModel: CreateRoadmapViewModel by viewModel()

    private var pointOfInterests: MutableList<PointOfInterest> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()

        registerObserver()

        setUpView(view)
    }

    private fun setUpView(view: View) {
        btCreatePoi = view.findViewById(R.id.btAddPointOfInterest)
        btCreatePoi.setOnClickListener {
            findNavController().navigate(
                R.id.createPointOfInterest, bundleOf(
                    NAVIGATION_KEY to findNavController().currentDestination?.id
                )
            )
        }

        rvPointOfInterest = view.findViewById(R.id.rvPointOfInterests)
        rvPointOfInterest.adapter = RoadmapAdapter(pointOfInterests)
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
        createRoadmapViewModel.saveRoadmapState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    showMessage("Cadastro realizado com sucesso")
                    requireActivity().supportFragmentManager.popBackStack()
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

        setFragmentResultListener("addPoi") { requestKey, bundle ->
            val poi = bundle.get("newPoi") as PointOfInterest
            pointOfInterests.add(poi)
            rvPointOfInterest.adapter?.notifyDataSetChanged()
        }
    }

}
