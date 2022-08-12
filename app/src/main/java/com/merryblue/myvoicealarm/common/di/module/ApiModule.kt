package com.merryblue.myvoicealarm.common.di.module

import com.merryblue.myvoicealarm.data.api.UserAlarmApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): UserAlarmApi {
        return retrofit.create<UserAlarmApi>(UserAlarmApi::class.java)
    }

}