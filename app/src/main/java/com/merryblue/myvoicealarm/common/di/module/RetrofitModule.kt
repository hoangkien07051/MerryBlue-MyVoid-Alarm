package com.merryblue.myvoicealarm.common.di.module

import android.content.Context
import com.merryblue.myvoicealarm.common.di.retrofit.RetrofitBuilder
import com.merryblue.myvoicealarm.common.utils.AppUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule() {

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext appContext: Context): Retrofit {
        return initRetrofit(appContext, AppUtils.getBaseUrl())
    }

    private fun initRetrofit(appContext: Context, baseUrl: String): Retrofit {
        return RetrofitBuilder().retrofit(appContext, baseUrl)
    }
}