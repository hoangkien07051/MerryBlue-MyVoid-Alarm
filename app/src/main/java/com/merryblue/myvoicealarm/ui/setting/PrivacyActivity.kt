package com.merryblue.myvoicealarm.ui.setting

import android.os.Bundle
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.common.extenstion.click
import com.merryblue.myvoicealarm.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_privacy.*

class PrivacyActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_privacy)
        btnBack.click {
            finish()
        }
    }
}