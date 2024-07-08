package com.example.apptodo.app

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.example.apptodo.data.database.AppDatabase
import com.example.apptodo.data.network.RetrofitService
import com.example.apptodo.data.network.mapper.CloudTodoItemToEntityMapper
import com.example.apptodo.data.network.utils.DataSyncWorker
import com.example.apptodo.data.network.utils.NetworkChecker
import com.example.apptodo.data.repository.DeviceNameRepository
import com.example.apptodo.data.repository.LastKnownRevisionRepository
import com.example.apptodo.data.repository.TodoItemsRepository
import com.example.apptodo.presentation.viewmodels.ItemListViewModelFactory
import com.example.apptodo.presentation.viewmodels.RedactorViewModelFactory


/**
 * Custom Application class for initializing and managing application-wide components and services.
 * It initializes the TodoItemsRepository, ViewModel factories, Room database, Retrofit service,
 * and manages background synchronization using WorkManager.
 */



class App : Application() {

    private lateinit var todoItemsRepository: TodoItemsRepository

    internal fun getTodoItemsRepository(): TodoItemsRepository {
        return todoItemsRepository
    }

    lateinit var itemListViewModelFactory: ItemListViewModelFactory
    lateinit var redactorViewModelFactory: RedactorViewModelFactory

    private lateinit var roomDataBase: AppDatabase
    private lateinit var deviceNameRepository: DeviceNameRepository
    private lateinit var lastKnownRevisionRepository: LastKnownRevisionRepository
    private lateinit var networkChecker: NetworkChecker


    private val cloudTodoItemToEntityMapper by lazy {
        CloudTodoItemToEntityMapper(deviceNameRepository)
    }

    override fun onCreate() {
        super.onCreate()

        lastKnownRevisionRepository = LastKnownRevisionRepository()
        networkChecker = NetworkChecker(applicationContext)
        roomDataBase = AppDatabase.getDatabase(this)
        deviceNameRepository = DeviceNameRepository(contentResolver)

        RetrofitService.initialize(lastKnownRevisionRepository)

        todoItemsRepository = TodoItemsRepository(
            roomDataBase.todoDao(),
            RetrofitService.createTodoBackendService(),
            networkChecker,
            cloudTodoItemToEntityMapper,
            lastKnownRevisionRepository
        )

        itemListViewModelFactory = ItemListViewModelFactory(todoItemsRepository, networkChecker)
        redactorViewModelFactory = RedactorViewModelFactory(todoItemsRepository)


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