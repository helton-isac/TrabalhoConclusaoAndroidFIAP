package com.fiap.meurole.map

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.fiap.meurole.R
import com.fiap.meurole.base.BaseFragment
import com.fiap.meurole.base.auth.NAVIGATION_KEY
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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

    private var permissionDenied = false

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val mapViewModel: MapViewModel by viewModel()

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()

        setUpView(view)
    }

    override fun onResume() {
        super.onResume()
        if (permissionDenied) {
            showMessage("Map permission not granted")
            permissionDenied = false
        }
    }

    private fun setUpView(view: View) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.viewMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        etSearch = view.findViewById(R.id.etMapSearch)
        btSearch = view.findViewById(R.id.btMapSearch)
        btSearch.setOnClickListener {
            val location = etSearch.text.toString()

            if (location.isNotBlank()) {
                val addressList = Geocoder(requireContext()).getFromLocationName(location, 1)
                if (addressList != null && addressList.count() > 0) {
                    val address: Address = addressList[0]
                    val latLong = LatLng(address.getLatitude(), address.getLongitude())
                    // TODO: Encontrado o local fazer requisição para encontrar os roteiros proximos da camera LatLong
                    mMap.addMarker(MarkerOptions().position(latLong).title(location))
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLong))
                } else {
                    showMessage("Location not found.")
                }
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        enableMyLocation()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when(requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation()
                } else {
                    permissionDenied = true
                }
            }
        }
    }

    private fun enableMyLocation() {
        if (!::mMap.isInitialized) return

        if (ContextCompat.checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissions(Array<String>(1, {ACCESS_FINE_LOCATION}), LOCATION_PERMISSION_REQUEST_CODE)
        }

        fusedLocationClient.lastLocation.addOnSuccessListener {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 15f))
        }
    }
}