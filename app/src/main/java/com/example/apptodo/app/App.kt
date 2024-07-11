package com.example.apptodo.app

import android.app.Application
import com.example.apptodo.data.network.utils.WorkManagerInitialize
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


/**
 * Custom Application class for initializing and managing application-wide components and services.
 * It initializes the TodoItemsRepository, ViewModel factories, Room database, Retrofit service,
 * and manages background synchronization using WorkManager.
 */

@HiltAndroidApp
class App : Application()
{

    @Inject
    lateinit var workManagerInitializer: WorkManagerInitialize

    override fun onCreate() {
        super.onCreate()

        workManagerInitializer.setupWorkManager()
    }
}