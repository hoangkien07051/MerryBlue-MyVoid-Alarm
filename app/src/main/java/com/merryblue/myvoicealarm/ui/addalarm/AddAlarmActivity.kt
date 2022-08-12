package com.merryblue.myvoicealarm.ui.addalarm

import android.os.Bundle
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class AddAlarmActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_add_alarm)
        if (null == savedInstanceState) {
            supportFragmentManager.beginTransaction()
                .add(R.id.containerAdd, AddAlarmFragment())
                .commit()
        }

    }

}