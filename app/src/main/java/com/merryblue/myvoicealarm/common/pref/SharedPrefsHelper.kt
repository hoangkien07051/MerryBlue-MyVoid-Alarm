package com.merryblue.myvoicealarm.common.pref

import android.content.Context
import com.google.gson.Gson

object SharedPrefsHelper {

    const val OPEN_APP_FIRST_TIME = "open_app_first_time"
    const val TITLE_FIRST_OPEN_APP = "onboard"
    const val SETTING_LANGUAGE = "english"
    const val MyPREFERENCES = "my-voice-alarm"


    val gson = Gson()

    fun remove(context: Context, prefName: String, key: String) {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE).edit().remove(key).apply()
    }

    fun saveString(context: Context, prefName: String, key: String, value: String?) {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE).edit().putString(key, value)
            .apply()
    }

    fun getString(context: Context, prefName: String, key: String): String? {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE).getString(key, null)
    }

    fun getString(context: Context, key: String): String? {
        return context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString(key, null)
    }

    fun saveInt(context: Context, prefName: String, key: String, value: Int) {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE).edit().putInt(key, value)
            .apply()
    }

    fun saveInt(context: Context, key: String, value: Int) {
        context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit().putInt(key, value)
            .apply()
    }

    fun getInt(context: Context, prefName: String, key: String): Int {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE).getInt(key, 0)
    }

    fun getInt(context: Context, key: String): Int {
        return context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getInt(key, 0)
    }

    fun saveBoolean(context: Context, prefName: String, key: String, value: Boolean) {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE).edit().putBoolean(key, value)
            .apply()
    }

    fun getBoolean(context: Context, prefName: String, key: String, value: Boolean): Boolean {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE).getBoolean(key, value)
    }

    fun getBoolean(context: Context,   key: String, value: Boolean): Boolean {
        return context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getBoolean(key, value)
    }

    fun saveString(context: Context, key: String, value: String?) {
        context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit().putString(key, value)
            .apply()
    }

    fun saveBoolean(context: Context, str: String?, z: Boolean) {
        val edit = context.getSharedPreferences(
            MyPREFERENCES,
            0
        ).edit()
        edit.putBoolean(str, z)
        edit.apply()
    }

}
