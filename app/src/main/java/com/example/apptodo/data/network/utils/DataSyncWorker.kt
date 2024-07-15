package com.example.apptodo.data.network.utils

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.apptodo.app.App
import com.example.apptodo.data.repository.TodoItemsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * A Worker class responsible for periodic synchronization of data between
 * a local database and a remote server.
 **/

@HiltWorker
class DataSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val todoItemsRepository: TodoItemsRepository
) : CoroutineWorker(context, workerParams) {


    override suspend fun doWork(): Result {
        return try {
            todoItemsRepository.synchronizeData()
            Result.success()
        } catch (e: Exception) {
            Log.d("AAA", "aaa")
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
