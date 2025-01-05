package com.faridnia.khoroojyar.presentation.settings.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.faridnia.khoroojyar.R

@Composable
fun SwitchDarkMode(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    SwitchSetting(
        title = stringResource(R.string.dark_mode),
        isChecked = isChecked,
        onCheckedChange = onCheckedChange
    )
}