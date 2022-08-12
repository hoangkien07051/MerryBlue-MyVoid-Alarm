package com.merryblue.myvoicealarm.data.model

import de.coldtea.smplr.smplralarm.models.WeekInfo

data class AlarmModel(
    var alarmId: Int? = null,
    var hour: Int? = null,
    var minute: Int? = null,
    var weekInfo: WeekInfo? = null,
    var isVibrate: Boolean? = null,
    var isEnable: Boolean? = null,
    var notes: String? = null
)