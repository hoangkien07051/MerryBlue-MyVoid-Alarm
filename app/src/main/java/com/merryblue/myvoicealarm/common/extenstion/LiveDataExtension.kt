package com.merryblue.myvoicealarm.common.extenstion

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    unObserve(liveData)
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

fun <T> LifecycleOwner.unObserve(liveData: LiveData<T>) {
    liveData.removeObservers(this)
}
