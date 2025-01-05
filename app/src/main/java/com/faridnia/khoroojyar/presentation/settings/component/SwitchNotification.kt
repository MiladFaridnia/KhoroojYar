package com.faridnia.khoroojyar.presentation.settings.component

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import com.faridnia.khoroojyar.R

@Composable
fun SwitchNotification(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val notificationPermission = Manifest.permission.POST_NOTIFICATIONS
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onCheckedChange(true) // Update the state if permission is granted
        } else {
            onCheckedChange(false) // Revert the toggle if permission is denied
        }
    }

    SwitchSetting(
        title = stringResource(R.string.notifications),
        isChecked = isChecked,
        onCheckedChange = { newValue ->
            if (newValue) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        notificationPermission
                    ) == PackageManager.PERMISSION_GRANTED

                ) {
                    onCheckedChange(true)
                } else {
                    notificationPermissionLauncher.launch(notificationPermission)
                }
            } else {
                onCheckedChange(false)
            }
        }
    )
}