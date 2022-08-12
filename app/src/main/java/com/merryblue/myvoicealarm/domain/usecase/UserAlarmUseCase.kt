package com.merryblue.myvoicealarm.domain.usecase

import com.merryblue.myvoicealarm.data.response.UserAlarmResponse
import com.merryblue.myvoicealarm.domain.repo.UserAlarmRepo
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserAlarmUseCase @Inject constructor() {

    @Inject
    lateinit var userAlarmRepo: UserAlarmRepo

    fun loadPhoto(): Single<List<UserAlarmResponse>> {
        return userAlarmRepo.loadPhoto()
    }
}