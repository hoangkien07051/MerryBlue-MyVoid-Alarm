package com.merryblue.myvoicealarm.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.merryblue.myvoicealarm.common.extenstion.LOADING

open class BaseViewModel(val app: Application)  : AndroidViewModel(app) {
    val mLoading = MutableLiveData<LOADING>() // k dung -> xoa di
    val mError = MutableLiveData<Throwable>()
}