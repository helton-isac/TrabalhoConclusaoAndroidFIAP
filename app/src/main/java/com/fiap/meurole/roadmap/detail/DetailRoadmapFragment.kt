package com.fiap.meurole.roadmap.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.fiap.meurole.R
import com.fiap.meurole.base.auth.BaseAuthFragment
import com.hitg.domain.entity.RequestState
import com.hitg.domain.entity.Roadmap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates


@ExperimentalCoroutinesApi
class DetailRoadmapFragment : BaseAuthFragment() {
    override val layout = R.layout.detail_roadmap_fragment

    private val viewModel: DetailRoadmapFragment by viewModel()

    private lateinit var roadmap: Roadmap
    private var imageResource by Delegates.notNull<Int>()

    private lateinit var tvDetailUserCreator: TextView
    private lateinit var ivDetailPhoto: ImageView
    private lateinit var tvDetailRoadmapTitle: TextView
    private lateinit var tvDetailRoadmapDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_roadmap_menu_action_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()
        setTitle(getString(R.string.app_name))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()

        roadmap = arguments?.getSerializable("roadmap") as Roadmap
        imageResource = arguments?.getInt("imageResource")!!

        setUpView(view)
    }

    private fun setUpView(view: View) {
        tvDetailUserCreator = view.findViewById(R.id.tvDetailUserCreator)
        tvDetailUserCreator.text = roadmap.creatorName
        ivDetailPhoto = view.findViewById(R.id.ivDetailPhoto)
        ivDetailPhoto.setImageResource(imageResource)
        tvDetailRoadmapTitle = view.findViewById(R.id.tvDetailRoadmapTitle)
        tvDetailRoadmapTitle.text = roadmap.name
        tvDetailRoadmapDescription = view.findViewById(R.id.tvDetailRoadmapDescription)
        tvDetailRoadmapDescription.text = roadmap.description
    }

    private fun registerObserver() {

        baseAuthViewModel.userLoggedState.observe(viewLifecycleOwner, { result ->
            when (result) {
                is RequestState.Loading -> showLoading()
                is RequestState.Success -> hideLoading()
                is RequestState.Error -> hideLoading()
            }
        })
        setFragmentResultListener("create_edit_roadmap") { requestKey, bundle ->
            setFragmentResult("create_edit_roadmap", bundle)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.detail_roadmap_action_menu_edit -> {
                findNavController().navigate(
                    R.id.action_detailRoadmapFragment_to_createRoadmapFragment,
                    bundleOf(
                        "roadmap" to roadmap
                    )
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

}