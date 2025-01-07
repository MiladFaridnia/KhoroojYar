package com.faridnia.khoroojyar

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.AudioAttributes
import android.net.Uri
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.faridnia.khoroojyar.presentation.notification.NotificationHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val sound = Uri.parse("android.resource://${packageName}/${R.raw.notif_sound}")
        val audioAttributes = AudioAttributes
            .Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
        val channel = NotificationChannel(
            NotificationHelper.CHANNEL_ID,
            "Work Exit Reminders",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setSound(sound, audioAttributes)
            vibrationPattern = longArrayOf(500, 500, 500, 500, 500)
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}
