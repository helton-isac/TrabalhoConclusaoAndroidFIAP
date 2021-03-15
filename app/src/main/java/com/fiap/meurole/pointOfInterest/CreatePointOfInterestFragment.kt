package com.fiap.meurole.pointOfInterest

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.fiap.meurole.R
import com.fiap.meurole.base.BaseFragment
import com.hitg.domain.entity.PointOfInterest
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class CreatePointOfInterestFragment : BaseFragment() {

    override val layout = R.layout.create_point_of_interest

    private lateinit var etName: EditText
    private lateinit var etDescription: EditText
    private lateinit var etLat: EditText
    private lateinit var etLong: EditText
    private lateinit var btAdd: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBackPressedAction()

        setUpView(view)
    }

    private fun setUpView(view: View) {
        etName = view.findViewById(R.id.etPointOfInterestName)
        etDescription = view.findViewById(R.id.etPointOfInterestDescription)
        etLat = view.findViewById(R.id.etLatitude)
        etLong = view.findViewById(R.id.etLongitude)
        btAdd = view.findViewById(R.id.btSavePointOfInterest)

        btAdd.setOnClickListener {
            val pointOfInterest = PointOfInterest(
                "",
                etLat.text.toString().toDouble(),
                etLong.text.toString().toDouble(),
                etName.text.toString(),
                etDescription.text.toString(),
                "")

            val bundle: Bundle
            bundle.putSerializable()
            setFragmentResult("addPoi", bundleOf("newPoi" to pointOfInterest))
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

}
