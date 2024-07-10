package com.example.apptodo.app

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.example.apptodo.data.dao.TodoDao
import com.example.apptodo.data.network.TodoBackend
import com.example.apptodo.data.network.mapper.CloudTodoItemToEntityMapper
import com.example.apptodo.data.network.utils.DataSyncWorker
import com.example.apptodo.data.network.utils.NetworkChecker
import com.example.apptodo.data.repository.DeviceNameRepository
import com.example.apptodo.data.repository.LastKnownRevisionRepository
import com.example.apptodo.data.repository.TodoItemsRepository
import com.example.apptodo.presentation.viewmodels.ItemListViewModelFactory
import com.example.apptodo.presentation.viewmodels.RedactorViewModelFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


/**
 * Custom Application class for initializing and managing application-wide components and services.
 * It initializes the TodoItemsRepository, ViewModel factories, Room database, Retrofit service,
 * and manages background synchronization using WorkManager.
 */

@HiltAndroidApp
class App : Application(), Configuration.Provider{

    @Inject
    internal lateinit var deviceNameRepository: DeviceNameRepository

    @Inject
    internal lateinit var networkChecker: NetworkChecker

    @Inject
    internal lateinit var lastKnownRevisionRepository: LastKnownRevisionRepository

    @Inject
    internal lateinit var cloudTodoItemToEntityMapper: CloudTodoItemToEntityMapper

    @Inject
    lateinit var todoDao: TodoDao

    @Inject
    lateinit var todoBackend: TodoBackend

    @Inject
    lateinit var todoItemsRepository: TodoItemsRepository

    @Inject
    lateinit var workerFactory: WorkerFactory


    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()




    override fun onCreate() {
        super.onCreate()



        setupWorkManager()
    }

    private fun setupWorkManager() {
        val syncWorkRequest = DataSyncWorker.createWorkRequest()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "DataSyncWork",
                ExistingPeriodicWorkPolicy.REPLACE,
                syncWorkRequest
            )
    }
}