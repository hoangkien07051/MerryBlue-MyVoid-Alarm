package com.merryblue.myvoicealarm.ui

import android.app.Application
import android.util.Log
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VoiceAlarmApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        AppLovinSdk.getInstance(this).mediationProvider = AppLovinMediationProvider.MAX
        AppLovinSdk.getInstance(this).initializeSdk {

        }
    }
}