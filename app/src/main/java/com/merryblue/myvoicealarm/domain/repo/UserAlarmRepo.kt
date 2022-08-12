package com.merryblue.myvoicealarm.domain.repo


import com.merryblue.myvoicealarm.data.response.UserAlarmResponse
import io.reactivex.rxjava3.core.Single

interface UserAlarmRepo {
    fun loadPhoto(): Single<List<UserAlarmResponse>>
}
