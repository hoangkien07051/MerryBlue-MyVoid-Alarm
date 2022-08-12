package com.merryblue.myvoicealarm.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.merryblue.myvoicealarm.common.extenstion.observe
import com.merryblue.myvoicealarm.databinding.FragmentSplashBinding
import com.merryblue.myvoicealarm.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    private val viewModel by viewModels<SplashViewModel>()
    private lateinit var dataBinding: FragmentSplashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dataBinding = FragmentSplashBinding.inflate(inflater)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun setUpView() {

    }

    override fun observable() {
        observe(viewModel.alarmData) { data ->

        }
    }

    override fun fireData() {

    }
}