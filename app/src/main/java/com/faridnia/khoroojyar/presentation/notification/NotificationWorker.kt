package com.faridnia.khoroojyar.presentation.notification

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.domain.use_case.notification.CalculateNotificationTimesUseCase
import com.faridnia.khoroojyar.util.toLocalTime
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalTime

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val calculateNotificationTimesUseCase: CalculateNotificationTimesUseCase,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val KEY_ENTER_TIME = "enterTime"
    }

    override suspend fun doWork(): Result {
        val enterTime = inputData.getString(KEY_ENTER_TIME)?.toLocalTime()
        val notificationTimes = calculateNotificationTimesUseCase(enterTime)

        notificationTimes.forEach { notificationExitTime ->
            val delay = Duration.between(LocalTime.now(), notificationExitTime.time)
            delay.toMillis().takeIf { it > 0 }?.let {
                delay(it)
                notificationHelper.showNotification(
                    context.getString(R.string.escape_alert),
                    context.getString(notificationExitTime.messageId) + notificationExitTime.extraMessage,
                    1
                )
            }
        }
        return Result.success()
    }
}