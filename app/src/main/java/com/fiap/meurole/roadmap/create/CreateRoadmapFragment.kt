package com.fiap.meurole.roadmap.create

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.RecyclerView
import com.fiap.meurole.R
import com.fiap.meurole.base.auth.BaseAuthFragment
import com.fiap.meurole.utils.DialogUtils
import com.google.android.material.textfield.TextInputEditText
import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class CreateRoadmapFragment : BaseAuthFragment() {

    override val layout = R.layout.create_roadmap_fragment

    private lateinit var etRoadmapName: TextInputEditText
    private lateinit var etRoadmapDescription: EditText
    private lateinit var rvPointOfInterest: RecyclerView
    private lateinit var btCreatePoi: Button
    private lateinit var btCreateRoadmap: Button

    private val viewModel: CreateRoadmapViewModel by viewModel()

    private var pointOfInterests: MutableList<PointOfInterest> = arrayListOf()

    override fun onResume() {
        super.onResume()
        setTitle(getString(R.string.create_roadmap))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()

        setUpView(view)
    }

    private fun setUpView(view: View) {
        etRoadmapName = view.findViewById(R.id.etRoadmapName)
        etRoadmapDescription = view.findViewById(R.id.etRoadmapDescription)

        rvPointOfInterest = view.findViewById(R.id.rvDetailPointOfInterests)
        rvPointOfInterest.adapter = PointOfInterestAdapter(pointOfInterests, clickListener = {
            findNavController().navigate(
                R.id.action_createRoadmapFragment_to_editPointOfInterest,
                bundleOf(
                    "pointOfInterest" to it
                )
            )
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoading()
                val position = viewHolder.adapterPosition
                viewModel.deletePointOfInterest(pointOfInterests[position])
            }
        }).attachToRecyclerView(rvPointOfInterest)

        btCreatePoi = view.findViewById(R.id.btAddPointOfInterest)
        btCreatePoi.setOnClickListener {
            findNavController().navigate(R.id.action_createRoadmapFragment_to_createPointOfInterest)
        }

        btCreateRoadmap = view.findViewById(R.id.btSaveRoadmap)
        btCreateRoadmap.setOnClickListener {
            showLoading()
            val roadmap =
                Roadmap(
                    "",
                    etRoadmapName.text.toString(),
                    etRoadmapDescription.text.toString(),
                    pointOfInterests,
                    "",
                    userLogged.name
                )
            viewModel.createRoadmap(roadmap)
        }
    }


    private fun registerObserver() {
        viewModel.saveRoadmapState.observe(viewLifecycleOwner, {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    DialogUtils.showToastMessage(
                        requireContext(),
                        getString(R.string.roadmap_registered_with_success)
                    )
                    findNavController().popBackStack()
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

        viewModel.deletePointOfInterestState.observe(viewLifecycleOwner, {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    DialogUtils.showToastMessage(
                        requireContext(),
                        getString(R.string.poi_deleted_with_success)
                    )
                    val index = pointOfInterests.indexOfFirst { poi -> poi.id == it.data }
                    pointOfInterests.removeAt(index)
                    rvPointOfInterest.adapter?.notifyDataSetChanged()
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
        baseAuthViewModel.userLoggedState.observe(viewLifecycleOwner, { result ->
            when (result) {
                is RequestState.Loading -> showLoading()
                is RequestState.Success -> hideLoading()
                is RequestState.Error -> hideLoading()
            }
        })

        setFragmentResultListener("addPoi") { requestKey, bundle ->
            val poi = bundle.get("poi") as PointOfInterest
            pointOfInterests.add(poi)
            rvPointOfInterest.adapter?.notifyDataSetChanged()
        }

        setFragmentResultListener("editPoi") { requestKey, bundle ->
            val editedPoi = bundle.get("poi") as PointOfInterest
            val index = pointOfInterests.indexOfFirst { poi -> poi.id == editedPoi.id }
            pointOfInterests.removeAt(index)
            pointOfInterests.add(index, editedPoi)
            rvPointOfInterest.adapter?.notifyDataSetChanged()
        }

    }

}
