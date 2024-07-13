package com.example.apptodo.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * Custom Application class for initializing and managing application-wide components and services.
 * It initializes the TodoItemsRepository, ViewModel factories, Room database, Retrofit service,
 * and manages background synchronization using WorkManager.
 */

@HiltAndroidApp
class App : Application()