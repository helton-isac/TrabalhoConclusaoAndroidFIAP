package com.fiap.meurole.map

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import com.fiap.meurole.R
import com.fiap.meurole.base.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MapFragment : BaseFragment(), OnMapReadyCallback {

    override val layout = R.layout.map_fragment

    private lateinit var mMap: GoogleMap
    private lateinit var etSearch: EditText
    private lateinit var btSearch: Button

    private val mapViewModel: MapViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()

        setUpView(view)
    }

    private fun setUpView(view: View) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.viewMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        etSearch = view.findViewById(R.id.etMapSearch)
        btSearch = view.findViewById(R.id.btMapSearch)
        btSearch.setOnClickListener {
//            val location = etSearch.text.toString()
//            var addressList: List<Address>? = null
//
//            if (location.isNotBlank()) {
//                val geocoder = Geocoder(this)
//                addressList = geocoder.getFromLocationName(location, 1)
//                val address: Address = addressList!![0]
//                val latLng = LatLng(address.getLatitude(), address.getLongitude())
//                mMap.addMarker(MarkerOptions().position(latLng).title(location))
//                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
//            }
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val pozale = LatLng(-22.226999, -45.937727)
        mMap.addMarker(
            MarkerOptions()
                .position(pozale)
                .title("Pouso Alegre - MG")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pozale))
    }
}