package com.merryblue.myvoicealarm.data.model

import com.google.gson.Gson
import java.io.Serializable

open class BaseModel : Serializable {

    fun toJson(): String {
        return Gson().toJson(this)
    }
}