package com.merryblue.myvoicealarm.ui.more

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.common.extenstion.click
import com.merryblue.myvoicealarm.common.extenstion.observe
import com.merryblue.myvoicealarm.common.utils.NavigationIntentHelper
import com.merryblue.myvoicealarm.databinding.FragmentMoreBinding
import com.merryblue.myvoicealarm.ui.base.BaseFragment
import com.merryblue.myvoicealarm.ui.menu.language.LanguageFragment
import com.merryblue.myvoicealarm.ui.setting.AboutActivity
import com.merryblue.myvoicealarm.ui.setting.PrivacyActivity
import com.merryblue.myvoicealarm.ui.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_more.*

@AndroidEntryPoint
class MoreFragment : BaseFragment() {

    private val viewModel by viewModels<MoreViewModel>()
    private lateinit var dataBinding: FragmentMoreBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dataBinding = FragmentMoreBinding.inflate(inflater)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    override fun setUpView() {
        initInterstitialAd()

        tv_setting.click {
            val intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)
        }
        tv_privacy.click {
            val intent = Intent(activity, PrivacyActivity::class.java)
            startActivity(intent)
        }

        tv_about.click {
            val intent = Intent(activity, AboutActivity::class.java)
            startActivity(intent)
        }

        tv_language.click {

        }

        tv_Share.click {
            startActivity(NavigationIntentHelper.getShareAppIntent())
        }

        tv_feedback.click {
            showInterstitialAd()
        }
    }

    override fun observable() {
        observe(viewModel.alarmData) { data ->

        }
    }

    override fun fireData() {

    }
}