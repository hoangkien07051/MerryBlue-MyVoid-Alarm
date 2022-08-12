package com.merryblue.myvoicealarm.common.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Vibrator
import android.util.Log
import androidx.core.content.ContextCompat
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.common.pref.VoiceAlarmPref
import com.merryblue.myvoicealarm.common.service.PlaySoundService
import de.coldtea.smplr.smplralarm.apis.SmplrAlarmAPI.Companion.SMPLR_ALARM_REQUEST_ID

class AlarmBroadcastReceiver : BroadcastReceiver() {

    private var player: MediaPlayer? = null
    private var vibratorService: Vibrator? = null
    override fun onReceive(context: Context, intent: Intent) {
        val requestId = intent.getIntExtra(SMPLR_ALARM_REQUEST_ID, -1)
        ContextCompat.startForegroundService(context, Intent(context, PlaySoundService::class.java))
        Log.i("AlarmBroadcastReceiver", "AlarmBroadcastReceiver received with id: $requestId")
        VoiceAlarmPref.setAlarmId(context, requestId)
    }
}