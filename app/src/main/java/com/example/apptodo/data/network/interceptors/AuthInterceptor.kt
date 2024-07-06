package com.example.apptodo.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor for adding Authorization header with Bearer token to outgoing requests.
 */

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Authorization", "Bearer Aranel")
        return chain.proceed(requestBuilder.build())
    }
}