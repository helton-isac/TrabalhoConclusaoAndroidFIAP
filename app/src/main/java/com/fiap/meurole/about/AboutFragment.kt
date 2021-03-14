package com.fiap.meurole.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fiap.meurole.R
import com.fiap.meurole.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment : BaseFragment() {

    override val layout = R.layout.about_fragment

    private val aboutViewModel: AboutViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.about_fragment, container, false)
    }


}