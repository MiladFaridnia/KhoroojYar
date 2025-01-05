package com.faridnia.khoroojyar.domain.use_case.notification

import androidx.annotation.StringRes
import java.time.LocalTime

data class NotificationExitTime(
    @StringRes val messageId: Int,
    val time: LocalTime,
    val extraMessage: String? = null
)
