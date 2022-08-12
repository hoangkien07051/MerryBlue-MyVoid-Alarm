package com.merryblue.myvoicealarm.common.di.retrofit

import android.annotation.SuppressLint
import android.content.Context
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(
    private val retrofit: RetrofitBuilder,
    private val context: Context,
    private val baseUrl: String,
) : Interceptor {

    @SuppressLint("CheckResult")
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authenticationRequest = request(originalRequest)
        return chain.proceed(authenticationRequest)

    }

    private fun request(originalRequest: Request): Request {
        val accessVersion = "BuildConfig.ACCESS_VERSION"
        val accessKey = "BuildConfig.ACCESS_KEY"
        return originalRequest.newBuilder()
            .header("Accept-Version", accessVersion)
            .header("Authorization", "Client-ID $accessKey")
            .build()

    }
}
