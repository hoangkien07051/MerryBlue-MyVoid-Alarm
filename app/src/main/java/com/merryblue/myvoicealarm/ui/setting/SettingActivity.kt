package com.merryblue.myvoicealarm.ui.setting

import android.annotation.SuppressLint
import android.os.Bundle
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.common.extenstion.click
import com.merryblue.myvoicealarm.common.pref.VoiceAlarmPref
import com.merryblue.myvoicealarm.data.model.SnoozeIntervalItem
import com.merryblue.myvoicealarm.databinding.ActivitySettingBinding
import com.merryblue.myvoicealarm.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingBinding
    val listItem: ArrayList<SnoozeIntervalItem> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        setUpView()
    }

    @SuppressLint("SetTextI18n")
    fun setUpView() {
        val snoozeChoose = VoiceAlarmPref.getSnoozeInterval(applicationContext)
        for (i in 1 until 13) {
            val minutes = i * 5
            val item = SnoozeIntervalItem(minutes, false)
            if (minutes == snoozeChoose) {
                item.isSelected = true
            }
            listItem.add(item)
        }

        tvSnoozeValue.text = "$snoozeChoose" + getString(R.string.minutes)
        btnBack.click {
            finish()
        }
        switch_Alarm_setting?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                title_alarm_setting_value.text = "ON"
            } else title_alarm_setting_value.text = "OFF"
        }

        switch_clock_setting?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                title_clock_alue.text = "True"
            } else title_clock_alue.text = "False"
        }
        rlSnooze.click {
            showDialogSnooze()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDialogSnooze() {
        SnoozeDialogFragment(listItem) { listTimeInterval ->
            val snooze = listTimeInterval.firstOrNull { n -> n.isSelected }
            if (snooze != null) {
                VoiceAlarmPref.setSnoozeInterval(applicationContext, snooze.minutes)
                tvSnoozeValue.text = "${snooze.minutes} " + getString(R.string.minutes)
            }
        }.show(supportFragmentManager, "snooze")
    }


}