package com.example.apptodo.domain

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.apptodo.app.App
import com.example.apptodo.data.repository.TodoItemsRepository
import java.util.concurrent.TimeUnit

class DataSyncWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val todoItemsRepository: TodoItemsRepository by lazy {
        (context.applicationContext as App).getTodoItemsRepository()
    }


    override suspend fun doWork(): Result {
        return try {
            todoItemsRepository.synchronizeData()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        fun createWorkRequest(): PeriodicWorkRequest {
            return PeriodicWorkRequestBuilder<DataSyncWorker>(8, TimeUnit.HOURS)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()
        }
    }
}