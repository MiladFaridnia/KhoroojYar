package com.faridnia.khoroojyar.domain.use_case.notification

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.faridnia.khoroojyar.domain.repository.DataStoreRepository
import com.faridnia.khoroojyar.presentation.notification.NotificationWorker
import com.faridnia.khoroojyar.util.toFormattedString
import kotlinx.coroutines.flow.first
import java.time.LocalTime
import javax.inject.Inject

class ScheduleNotificationUseCase @Inject constructor(
    private val workManager: WorkManager,
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(enterTime: LocalTime) {
        if (dataStoreRepository.getTimeSettings().first().areNotificationsEnabled) {
            val inputData =
                workDataOf(NotificationWorker.KEY_ENTER_TIME to enterTime.toFormattedString())
            val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInputData(inputData)
                .build()
            workManager.enqueueUniqueWork(
                "NotificationWorker",
                ExistingWorkPolicy.REPLACE,
                workRequest
            )
        }
    }
}