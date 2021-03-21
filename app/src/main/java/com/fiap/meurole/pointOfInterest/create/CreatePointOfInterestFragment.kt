package com.fiap.meurole.pointOfInterest.create

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.fiap.meurole.R
import com.fiap.meurole.base.auth.BaseAuthFragment
import com.fiap.meurole.utils.DialogUtils
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.hitg.domain.entity.PointOfInterest
import com.hitg.domain.entity.RequestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class CreatePointOfInterestFragment : BaseAuthFragment(), OnMapReadyCallback {

    override val layout = R.layout.create_edit_point_of_interest_fragment

    private lateinit var etName: EditText
    private lateinit var etDescription: EditText
    private lateinit var etTelephone: EditText
    private lateinit var btCall: Button
    private lateinit var mMap: GoogleMap
    private lateinit var btAdd: Button

    private var mLatLng: LatLng? = null

    private val viewModel: CreatePointOfInterestViewModel by viewModel()

    override fun onResume() {
        super.onResume()
        setTitle(getString(R.string.add_point_of_interest))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()

        setUpView(view)
    }

    private fun setUpView(view: View) {
        etName = view.findViewById(R.id.etPointOfInterestName)
        etDescription = view.findViewById(R.id.etPointOfInterestDescription)
        etTelephone = view.findViewById(R.id.etTelephone)

        btCall = view.findViewById(R.id.btCall)
        btCall.setOnClickListener {
            if (etTelephone.text.isNotBlank()) {
                val number = etTelephone.text.toString().trim()
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(number)))
                startActivity(intent)
            }
        }

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
                    description = etDescription.text.toString(),
                    telephone = etTelephone.text.toString()
                )

                viewModel.create(poi)
            }
        }
    }

    private fun registerObserver() {
        viewModel.poiState.observe(viewLifecycleOwner, {
            when (it) {
                is RequestState.Success -> {
                    hideLoading()
                    DialogUtils.showToastMessage(
                        requireContext(),
                        getString(R.string.poi_registered_with_success)
                    )

                    setFragmentResult("addPoi", bundleOf("poi" to it.data))
                    findNavController().popBackStack()
                }
                is RequestState.Error -> {
                    hideLoading()
                    DialogUtils.showErrorMessage(requireContext(), it.throwable.message)
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
                        { dialog, _ -> dialog.dismiss() })
                    alertDialog.show()
                }
            }
        })
    }

}
