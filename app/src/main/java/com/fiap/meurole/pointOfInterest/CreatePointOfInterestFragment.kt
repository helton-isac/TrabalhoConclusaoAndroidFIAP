package com.fiap.meurole.pointOfInterest

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
class CreatePointOfInterestFragment : BaseFragment() {

    override val layout = R.layout.create_point_of_interest_fragment

    private lateinit var etName: EditText
    private lateinit var etDescription: EditText
    private lateinit var etLat: EditText
    private lateinit var etLong: EditText
    private lateinit var btAdd: Button

    private val viewModel: CreatePointOfInterestViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()

        registerObserver()

        setUpView(view)
    }

    private fun setUpView(view: View) {
        etName = view.findViewById(R.id.etPointOfInterestName)
        etDescription = view.findViewById(R.id.etPointOfInterestDescription)
        etLat = view.findViewById(R.id.etLatitude)
        etLong = view.findViewById(R.id.etLongitude)
        btAdd = view.findViewById(R.id.btSavePointOfInterest)

        btAdd.setOnClickListener {
            val poi = PointOfInterest(
                id = "",
                latitude = etLat.text.toString().toDouble(),
                longitude = etLong.text.toString().toDouble(),
                name = etName.text.toString(),
                description = etDescription.text.toString())

            viewModel.createPointOfInterest(poi)
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
                    showMessage("Ponto de interesse cadastrado")

                    setFragmentResult("addPoi", bundleOf("newPoi" to it.data))
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
