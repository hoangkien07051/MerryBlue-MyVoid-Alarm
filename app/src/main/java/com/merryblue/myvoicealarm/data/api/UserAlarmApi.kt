package com.merryblue.myvoicealarm.data.api

import com.merryblue.myvoicealarm.data.response.UserAlarmResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserAlarmApi {

    @GET("photos/")
    fun getPhotos(@Query("page") page: String, @Query("per_page") itemsPerPager: String): Single<List<UserAlarmResponse>>

}
