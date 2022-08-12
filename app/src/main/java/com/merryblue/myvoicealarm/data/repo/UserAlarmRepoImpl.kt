package com.merryblue.myvoicealarm.data.repo

import com.merryblue.myvoicealarm.data.api.UserAlarmApi
import com.merryblue.myvoicealarm.data.response.UserAlarmResponse
import com.merryblue.myvoicealarm.domain.repo.UserAlarmRepo
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class UserAlarmRepoImpl @Inject constructor() : UserAlarmRepo {

    @Inject
    lateinit var userAlarmApi: UserAlarmApi

    override fun loadPhoto(): Single<List<UserAlarmResponse>> {
        return userAlarmApi.getPhotos("0","20")
    }

}
