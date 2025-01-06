package com.faridnia.khoroojyar.presentation.settings.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

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

@LightAndDarkPreview
@Composable
private fun SwitchDarkModePreview() {
    KhoroojYarTheme {
        SwitchDarkMode(isChecked = true, onCheckedChange = {})
    }
}