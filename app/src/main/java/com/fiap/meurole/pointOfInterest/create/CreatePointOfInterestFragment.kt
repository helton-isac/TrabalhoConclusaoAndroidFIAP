package com.fiap.meurole.pointOfInterest.create

import android.app.AlertDialog
import android.content.DialogInterface
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
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class CreatePointOfInterestFragment : BaseFragment(), OnMapReadyCallback {

    override val layout = R.layout.create_edit_point_of_interest_fragment

    private lateinit var etName: EditText
    private lateinit var etDescription: EditText
    private lateinit var mMap: GoogleMap
    private lateinit var btAdd: Button

    private var mLatLng: LatLng? = null

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

        val mapFragment = childFragmentManager.findFragmentById(R.id.viewMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btAdd = view.findViewById(R.id.btSavePointOfInterest)

        btAdd.setOnClickListener {
            if (mLatLng != null) {
                val poi = PointOfInterest(
                    id = "",
                    latitude = mLatLng!!.latitude,
                    longitude = mLatLng!!.longitude,
                    name = etName.text.toString(),
                    description = etDescription.text.toString()
                )

                viewModel.create(poi)
            }
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

                    setFragmentResult("addPoi", bundleOf("poi" to it.data))
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        initializeAutocompleteFragment()
    }

    private fun initializeAutocompleteFragment() {
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
            )
        )

        autocompleteFragment.setHint("Pesquise um local")

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                mMap.clear()
                val latLng = place.latLng

                if (latLng != null) {
                    mLatLng = latLng
                    val marker = MarkerOptions().position(latLng).title(place.name)
                    mMap.addMarker(marker)

                    val builder = LatLngBounds.Builder()
                    builder.include(marker.position)

                    val bounds = builder.build()
                    val padding = 100
                    val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)

                    mMap.animateCamera(cameraUpdate)
                }
            }

            override fun onError(status: Status) {
                if (status != Status.RESULT_CANCELED) {
                    val alertDialog: AlertDialog = AlertDialog.Builder(requireContext()).create()
                    alertDialog.setTitle("Erro")
                    alertDialog.setMessage("Erro ao buscar local")
                    alertDialog.setButton(
                        AlertDialog.BUTTON_NEUTRAL,
                        "OK",
                        DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })
                    alertDialog.show()
                }
            }
        })
    }

}
