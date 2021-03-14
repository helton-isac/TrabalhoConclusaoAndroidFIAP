package com.fiap.meurole.about

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.fiap.meurole.BuildConfig
import com.fiap.meurole.R
import com.fiap.meurole.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class AboutFragment : BaseFragment() {

    override val layout = R.layout.about_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView(view)
    }

    private fun setUpView(view: View) {
        val tvVersion: TextView = view.findViewById(R.id.tvVersion)
        tvVersion.text = getString(R.string.version, BuildConfig.VERSION_NAME)
    }


}