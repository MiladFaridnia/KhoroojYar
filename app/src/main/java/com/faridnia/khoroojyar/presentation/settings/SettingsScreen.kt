package com.faridnia.khoroojyar.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.component.CustomText
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.component.TimePickerDialog
import com.faridnia.khoroojyar.presentation.component.bottom_navigation.BottomNavigationBar
import com.faridnia.khoroojyar.presentation.settings.component.SimpleSettingsItem
import com.faridnia.khoroojyar.presentation.settings.component.SwitchDarkMode
import com.faridnia.khoroojyar.presentation.settings.component.SwitchNotification
import com.faridnia.khoroojyar.presentation.settings.component.TimeSettingsItem
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme
import com.faridnia.khoroojyar.presentation.util.Screen
import com.faridnia.khoroojyar.util.toFormattedString
import com.jrg.app.ui.component.snackbar.CustomScaffold

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                SettingsViewModel.UiEvent.NavigateToProfile -> {
                    navController.navigate(Screen.Profile.route)
                }
            }
        }
    }
    CustomScaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        SettingsContent(state = state, onEvent = viewModel::onEvent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(state: SettingState, onEvent: (SettingsEvent) -> Unit) {

    Box(
        Modifier
            .fillMaxSize()
            .background(color = colorScheme.primaryContainer.copy(0.5f))
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
                .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
                .background(color = colorScheme.primary)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            HeaderSection()

            ElevatedCard(
                modifier = Modifier.fillMaxHeight(),
                shape = RoundedCornerShape(
                    topStart = 12.dp, topEnd = 12.dp, bottomStart = 0.dp, bottomEnd = 0.dp
                ),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = colorScheme.primaryContainer
                )
            ) {
                SimpleSettingsItem(
                    modifier = Modifier.padding(vertical = 4.dp),
                    title = stringResource(R.string.profile),
                    iconId = R.drawable.ic_profile_tick,
                    onClick = { onEvent(SettingsEvent.ProfileClicked) })

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                    thickness = 1.dp, color = colorScheme.onSurface
                )

                SwitchDarkMode(
                    isChecked = state.isDark ?: isSystemInDarkTheme(),
                    onCheckedChange = { onEvent(SettingsEvent.DarkModeClicked(it)) }
                )

                SwitchNotification(
                    isChecked = state.areNotificationsEnabled,
                    onCheckedChange = { onEvent(SettingsEvent.NotificationsToggled(it)) }
                )

                TimeSettingsItem(
                    title = stringResource(id = com.faridnia.khoroojyar.R.string.earliest_start),
                    time = state.earliestStart?.toFormattedString(),
                    onClick = { onEvent(SettingsEvent.EarliestStartClicked) }
                )

                TimeSettingsItem(
                    title = stringResource(id = com.faridnia.khoroojyar.R.string.latest_start),
                    time = state.latestStart?.toFormattedString(),
                    onClick = { onEvent(SettingsEvent.LatestStartClicked) }
                )

                TimeSettingsItem(
                    title = stringResource(id = com.faridnia.khoroojyar.R.string.work_duration),
                    time = state.workDuration?.toFormattedString(),
                    onClick = { onEvent(SettingsEvent.WorkDurationClicked) }
                )
            }
        }

        if (state.showTimePickerDialog) {
            TimePickerDialog(
                time = state.currentTimeForTimePicker,
                onConfirm = {
                    onEvent(SettingsEvent.TimeConfirmed(it.hour, it.minute))
                    onEvent(SettingsEvent.TimeDialogDismissed)
                },
                onDismiss = {
                    onEvent(SettingsEvent.TimeDialogDismissed)
                }
            )
        }
    }
}

@Composable
private fun HeaderSection() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_setting),
            contentDescription = null,
            tint = colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxHeight()
                .size(45.dp),
        )
        Spacer(Modifier.width(8.dp))
        CustomText(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.displaySmall,
            color = colorScheme.onPrimary
        )
    }
}

@LightAndDarkPreview
@Composable
private fun SettingsScreenPreview() {
    KhoroojYarTheme {
        SettingsContent(state = SettingState(), onEvent = {})
    }
}