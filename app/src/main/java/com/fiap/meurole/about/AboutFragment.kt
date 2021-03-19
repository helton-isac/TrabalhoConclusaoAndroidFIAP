package com.fiap.meurole.about

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.fiap.meurole.BuildConfig
import com.fiap.meurole.R
import com.fiap.meurole.base.BaseFragment
import com.hitg.domain.entity.RequestState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class AboutFragment : BaseFragment() {

    override val layout = R.layout.about_fragment

    private lateinit var tvSlogan: TextView

    private val aboutViewModel: AboutViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(view)

        registerObserver()

        aboutViewModel.getSlogan()
    }

    private fun setUpView(view: View) {
        val tvVersion: TextView = view.findViewById(R.id.tvVersion)
        tvVersion.text = getString(
            R.string.version,
            BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE.toString()
        )

        tvSlogan = view.findViewById(R.id.tvSlogan)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.about_menu_action_bar, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about_action_menu_share -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message))
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerObserver() {
        aboutViewModel.slogan.observe(viewLifecycleOwner, Observer { it ->
            when (it) {
                is RequestState.Success -> {
                    tvSlogan.text = it.data
                }
                else -> {

                }
            }
        })
    }

}