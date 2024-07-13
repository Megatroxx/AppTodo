package com.example.apptodo.data.network

import com.example.apptodo.data.network.interceptors.AuthInterceptor
import com.example.apptodo.data.network.interceptors.LastKnownRevisionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitService(
        authInterceptor: AuthInterceptor,
        lastKnownRevisionInterceptor: LastKnownRevisionInterceptor
    ): RetrofitServ {
        return RetrofitServ(authInterceptor, lastKnownRevisionInterceptor)
    }

    @Provides
    @Singleton
    fun provideTodoBackend(retrofitService: RetrofitServ): TodoBackend {
        return retrofitService.createTodoBackendService()
    }
}