package com.example.apptodo.data.network.utils

import androidx.hilt.work.HiltWorkerFactory
import androidx.work.WorkerFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SyncWorkerModule {

    @Binds
    abstract fun bindWorkerFactory(hiltWorkerFactory: HiltWorkerFactory): WorkerFactory
}