package com.merryblue.myvoicealarm.common.di.retrofit

import android.content.Context
import com.merryblue.myvoicealarm.common.di.network.NullOnEmptyConverterFactory
import com.merryblue.myvoicealarm.common.di.network.UnsafeOkHttpClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBuilder {

    fun retrofit(context: Context, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createClient(context, baseUrl))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    private fun createClient(context: Context, baseUrl: String): OkHttpClient {
        val httpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
        httpClient.connectTimeout(1, TimeUnit.MINUTES)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.writeTimeout(20, TimeUnit.SECONDS)
        httpClient.addInterceptor(
            AuthInterceptor(this, context, baseUrl)
        )
//        if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
//        }
        return httpClient.build()
    }
}
