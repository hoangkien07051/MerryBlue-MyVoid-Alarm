package com.merryblue.myvoicealarm.ui.splash

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.merryblue.myvoicealarm.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SplashViewModel @javax.inject.Inject constructor(app: Application) : BaseViewModel(app) {

    val alarmData = MutableLiveData<String>()
}