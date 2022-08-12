package com.merryblue.myvoicealarm.data.response

import com.google.gson.annotations.SerializedName

class UseAlarmSearchResultResponse(
    @SerializedName("results")
    val photos: List<UserAlarmResponse>? = null
)