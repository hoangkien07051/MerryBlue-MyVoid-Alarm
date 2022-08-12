package com.merryblue.myvoicealarm.common.pref

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.merryblue.myvoicealarm.data.model.AlarmModel
import de.coldtea.smplr.smplralarm.models.AlarmItem
import java.lang.reflect.Type

object VoiceAlarmPref {

    const val KEY_ALARM_LIST = "KEY_ALARM_LIST"
    const val KEY_SNOOZE_INTERVAL = "KEY_SNOOZE_INTERVAL"
    const val KEY_FULL_TIME_MODE = "KEY_FULL_TIME_MODE"
    const val KEY_ALARM_ID = "KEY_ALARM_ID"

    fun getListAlarms(context: Context): ArrayList<AlarmItem>? {
        return try {
            val userPref = SharedPrefsHelper.getString(context, KEY_ALARM_LIST)
            val type: Type = object : TypeToken<ArrayList<AlarmItem>>() {}.type
            val user: ArrayList<AlarmItem> = Gson().fromJson(userPref, type)
            user
        } catch (e: Exception) {
            null
        }
    }

    fun setListAlarms(context: Context, listAlarms: ArrayList<AlarmItem>) {
        val alarms = SharedPrefsHelper.gson.toJson(listAlarms)
        SharedPrefsHelper.saveString(context, KEY_ALARM_LIST, alarms)
    }

    fun setSnoozeInterval(context: Context, snooze: Int) {
        SharedPrefsHelper.saveInt(context, KEY_SNOOZE_INTERVAL, snooze)

    }

    fun getSnoozeInterval(context: Context): Int {
        var snooze = SharedPrefsHelper.getInt(context, KEY_SNOOZE_INTERVAL)
        if (snooze < 5) { // Min Snooze
            snooze = 5
        }
        if (snooze > 30) {// Max Snooze
            snooze = 30
        }
        return snooze
    }

    fun setTimeMode(context: Context, is24H: Boolean) {
        SharedPrefsHelper.saveBoolean(context, KEY_FULL_TIME_MODE, is24H)
    }

    /**
     * true = 24h - Long time
     * false = 12h - short time
     */
    fun getTimeMode(context: Context): Boolean {
        return SharedPrefsHelper.getBoolean(context, KEY_FULL_TIME_MODE, false)
    }

    fun setAlarmId(context: Context, alarmId: Int) {
        SharedPrefsHelper.saveInt(context, KEY_ALARM_ID, alarmId)
    }

    fun getAlarmId(context: Context): Int {
        return SharedPrefsHelper.getInt(context, KEY_ALARM_ID)
    }

}
