package com.merryblue.myvoicealarm.data.response

import com.google.gson.annotations.SerializedName

class UserAlarmResponse(
    @SerializedName("urls")
    val urlResponse: UrlResponse? = null,

    @SerializedName("user")
    val userResponse: UserResponse? = null,
)

class UrlResponse(
    @SerializedName("regular")
    val regular: String? = null,

    @SerializedName("small")
    val small: String? = null
)

class UserResponse(
    @SerializedName("username")
    val userName: String? = null,

    @SerializedName("name")
    val name: String? = null,
)