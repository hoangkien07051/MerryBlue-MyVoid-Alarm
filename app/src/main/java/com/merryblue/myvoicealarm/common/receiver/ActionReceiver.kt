package com.merryblue.myvoicealarm.common.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.merryblue.myvoicealarm.common.pref.VoiceAlarmPref
import com.merryblue.myvoicealarm.common.service.PlaySoundService
import com.merryblue.myvoicealarm.ui.addalarm.AddAlarmViewModel.Companion.ACTION_DISMISS
import com.merryblue.myvoicealarm.ui.addalarm.AddAlarmViewModel.Companion.ACTION_NOTIFICATION_DISMISS
import com.merryblue.myvoicealarm.ui.addalarm.AddAlarmViewModel.Companion.ACTION_SNOOZE
import com.merryblue.myvoicealarm.ui.addalarm.AddAlarmViewModel.Companion.HOUR
import com.merryblue.myvoicealarm.ui.addalarm.AddAlarmViewModel.Companion.MINUTE
import de.coldtea.smplr.smplralarm.apis.SmplrAlarmAPI.Companion.SMPLR_ALARM_NOTIFICATION_ID
import de.coldtea.smplr.smplralarm.smplrAlarmUpdate
import java.util.*

class ActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra(SMPLR_ALARM_NOTIFICATION_ID, -1)
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val now = getHourAndMinute()
        // stop play sound
        context.stopService(Intent(context, PlaySoundService::class.java))
        if (intent.action == ACTION_SNOOZE) {
            val alarmList = VoiceAlarmPref.getListAlarms(context)
            val alarmItem = alarmList?.firstOrNull { n -> n.requestId == notificationId }
            //TODO Add SNOOZE -> from Setting Screen
            val snoozeLocal = VoiceAlarmPref.getSnoozeInterval(context)
            val updatedTime = addTimeSnoozeInterval(snoozeLocal, intent.getIntExtra(HOUR, now.first), intent.getIntExtra(MINUTE, now.second))

            notificationManager.cancel(notificationId)

//            smplrAlarmSet(context) {
//                hour { updatedTime.first }
//                min { updatedTime.second }
//            }
            smplrAlarmUpdate(context) {
                requestCode { alarmItem?.requestId ?: -1 }
                hour { updatedTime.first }
                min { updatedTime.second }
            }
        }
        if (intent.action == ACTION_DISMISS) {
            notificationManager.cancel(notificationId)
        }
        if (intent.action == ACTION_NOTIFICATION_DISMISS) {
            Log.i("a", " Moin --> Dismissed notification: $notificationId")
        }
    }

    /**
     * add Snooze Interval
     */
    private fun addTimeSnoozeInterval(snoozeLocal: Int, hour: Int, minute: Int): Pair<Int, Int> {
        var mMinute = minute + snoozeLocal
        var mHour = hour

        if (mMinute == 60) {
            mMinute -= 60
            mHour += 1
        }

        if (mHour > 23)
            mHour = 0

        return mHour to mMinute
    }

    private fun getHourAndMinute(): Pair<Int, Int> = Calendar.getInstance().let {
        it.get(Calendar.HOUR_OF_DAY) to it.get(Calendar.MINUTE)
    }
}