package com.example.apptodo.di

import androidx.hilt.work.HiltWorkerFactory
import androidx.work.WorkerFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {

    @Provides
    fun provideWorkerFactory(hiltWorkerFactory: HiltWorkerFactory): WorkerFactory {
        return hiltWorkerFactory
    }
}