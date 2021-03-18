package com.fiap.meurole.pointOfInterest.edit

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import com.fiap.meurole.R
import com.fiap.meurole.base.BaseFragment
import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class EditPointOfInterestFragment : BaseFragment() {

    override val layout = R.layout.create_edit_point_of_interest_fragment

    private lateinit var etName: EditText
    private lateinit var etDescription: EditText
    private lateinit var etLat: EditText
    private lateinit var etLong: EditText
    private lateinit var btAdd: Button

    private val viewModel: EditPointOfInterestViewModel by viewModel()

    private lateinit var pointOfInterest: PointOfInterest

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()

        registerObserver()

        pointOfInterest = arguments?.getSerializable("pointOfInterest") as PointOfInterest
        setUpView(view)
    }

    private fun setUpView(view: View) {
        etName = view.findViewById(R.id.etPointOfInterestName)
        etName.setText(pointOfInterest.name)

        etDescription = view.findViewById(R.id.etPointOfInterestDescription)
        etDescription.setText(pointOfInterest.description)

        etLat = view.findViewById(R.id.etLatitude)
        etLat.setText(pointOfInterest.latitude.toString())

        etLong = view.findViewById(R.id.etLongitude)
        etLong.setText(pointOfInterest.longitude.toString())

        btAdd = view.findViewById(R.id.btSavePointOfInterest)

        btAdd.setOnClickListener {
            val poi = PointOfInterest(
                id = pointOfInterest.id,
                latitude = etLat.text.toString().toDouble(),
                longitude = etLong.text.toString().toDouble(),
                name = etName.text.toString(),
                description = etDescription.text.toString())

            viewModel.edit(poi)
        }
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
        viewModel.poiState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    showMessage("Ponto de interesse editado")
                    setFragmentResult("editPoi", bundleOf("poi" to it.data))
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
    }

}
