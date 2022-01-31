package com.example.codingapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.example.codingapp.initializers.AppInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

import com.example.codingapp.jobs.SyncWorkManager
import java.util.concurrent.TimeUnit


@HiltAndroidApp
class MainApp : Application(), Configuration.Provider {

    @Inject
    lateinit var initializers: AppInitializer

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        initializers.init(this)
        WorkManager.initialize(this, workManagerConfiguration)
        startSyncWorker()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()
    }

    private fun startSyncWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val sendDataBuilder: PeriodicWorkRequest.Builder =
            PeriodicWorkRequest.Builder(SyncWorkManager::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
        val periodicWorkRequest = sendDataBuilder.build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "Sync Data",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )

    }

}