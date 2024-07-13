package com.example.apptodo.data.network.utils

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WorkManagerInitialize @Inject constructor(
    @ApplicationContext private val context: Context,
    private val workerFactory: HiltWorkerFactory
) {

    fun setupWorkManager() {
        WorkManager.initialize(context, Configuration.Builder().setWorkerFactory(workerFactory).build())
        val syncWorkRequest = DataSyncWorker.createWorkRequest()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "DataSyncWork",
                ExistingPeriodicWorkPolicy.REPLACE,
                syncWorkRequest
            )
    }
}
