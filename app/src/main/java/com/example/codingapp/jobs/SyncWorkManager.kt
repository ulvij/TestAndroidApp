package com.example.codingapp.jobs

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.codingapp.R
import com.example.codingapp.domain.usecase.CheckReachTargetsUseCase
import com.example.codingapp.domain.usecase.SyncBtcPriceUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltWorker
class SyncWorkManager @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted userParameters: WorkerParameters
) : CoroutineWorker(context, userParameters) {

    @Inject
    lateinit var syncBtcPriceUseCase: SyncBtcPriceUseCase

    @Inject
    lateinit var checkReachTargetsUseCase: CheckReachTargetsUseCase


    companion object {
        const val NOTIFICATION_CHANNEL = "app_channel_01"
    }

    override suspend fun doWork(): Result {
        return try {

            val progress = "Starting Download"
            setForeground(createForegroundInfo(progress))

            syncBtcPriceUseCase.execute(Unit) {}

            checkReachTargetsUseCase.execute(Unit) {
                onSuccess = { reached ->
                    if (reached) {
                        notifyAboutTargets()
                    }
                }
            }

            Result.success()
        } catch (e: Exception) {
            Timber.e(e)
            Result.failure()
        }
    }

    private fun createForegroundInfo(progress: String): ForegroundInfo {
        val title = applicationContext.getString(R.string.app_name)
        val cancel = applicationContext.getString(R.string.cancel)
        // This PendingIntent can be used to cancel the worker
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(id)

        // Create a Notification channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL,
                context.getString(R.string.app_channel),
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager: NotificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(progress)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            // Add the cancel action to the notification which can
            // be used to cancel the worker
            .addAction(android.R.drawable.ic_delete, cancel, intent)
            .build()

        return ForegroundInfo(
            1,
            notification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
        )
    }

    private fun notifyAboutTargets() {

        val notificationManager: NotificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Create a Notification channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL,
                context.getString(R.string.app_channel),
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.apply {

            val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
                .setContentTitle("Reached!")
                .setContentText("BTC price reached targets you configured")
                .setSmallIcon(R.drawable.ic_launcher_foreground)

            notify(0, notification.build())
        }
    }


}