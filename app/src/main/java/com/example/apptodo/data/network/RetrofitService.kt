package com.example.apptodo.data.network

import com.example.apptodo.data.network.interceptors.AuthInterceptor
import com.example.apptodo.data.network.interceptors.LastKnownRevisionInterceptor
import com.example.apptodo.data.repository.LastKnownRevisionRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


/**
 * Singleton object responsible for managing the Retrofit instance and creating
 * the TodoBackend service interface.
 */


object RetrofitService {

    private lateinit var lastKnownRevisionRepository: LastKnownRevisionRepository

    private val retrofit = Retrofit.Builder()
        .baseUrl(TodoBackend.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .addInterceptor(LastKnownRevisionInterceptor(lastKnownRevisionRepository))
                .addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .build()

    internal fun initialize(lastKnownRevisionRepository: LastKnownRevisionRepository) {
        this.lastKnownRevisionRepository = lastKnownRevisionRepository
    }

    internal fun createTodoBackendService(): TodoBackend {
        return retrofit.create(TodoBackend::class.java)
    }
}