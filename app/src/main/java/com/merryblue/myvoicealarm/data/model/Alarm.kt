package com.merryblue.myvoicealarm.data.model

import de.coldtea.smplr.smplralarm.models.WeekInfo

data class Alarm(
    var hour: Int?,
    var minute: Int?,
    var weekInfo: WeekInfo?,
    var isActive: Boolean?,
    var isVibrate: Boolean?,
    var nameAlarm: String?,
    var voicePath: String?
)