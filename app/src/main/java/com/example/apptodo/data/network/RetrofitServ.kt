package com.example.apptodo.data.network

import com.example.apptodo.data.network.interceptors.AuthInterceptor
import com.example.apptodo.data.network.interceptors.LastKnownRevisionInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitServ @Inject constructor(
    private val authInterceptor: AuthInterceptor,
    private val lastKnownRevisionInterceptor: LastKnownRevisionInterceptor
) {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(TodoBackend.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .addInterceptor(lastKnownRevisionInterceptor)
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .build()
    }

    fun createTodoBackendService(): TodoBackend {
        return retrofit.create(TodoBackend::class.java)
    }
}