package com.faridnia.khoroojyar.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.calculate_days_off.CalculateRemainedDaysOff
import com.faridnia.khoroojyar.presentation.component.CustomBox
import com.faridnia.khoroojyar.presentation.component.CustomCard
import com.faridnia.khoroojyar.presentation.component.CustomText
import com.faridnia.khoroojyar.presentation.component.DateButton
import com.faridnia.khoroojyar.presentation.component.DateButtonType
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.component.TimePickerDialog
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExitTimeCalculatorScreen(viewModel: ExitTimeViewModel = viewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var showEnterTimePickerDialog by remember { mutableStateOf(false) }
    var showExitTimePickerDialog by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.uiEvents) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                ExitTimeViewModel.UiEvent.ShowBottomSheet -> isBottomSheetVisible = true
                ExitTimeViewModel.UiEvent.HideBottomSheet -> isBottomSheetVisible = false
            }
        }
    }

    val onTimeConfirm: (TimePickerState, (String) -> Unit, (Boolean) -> Unit) -> Unit =
        { timePickerState, onTimeChange, closeDialog ->
            val selectedTime =
                String.format(Locale.US, "%02d:%02d", timePickerState.hour, timePickerState.minute)
            onTimeChange(selectedTime)
            closeDialog(false)
        }

    CustomBox(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CustomCard(modifier = Modifier.padding(8.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DateButton(
                    label = state.enterTimeInput.ifEmpty { stringResource(R.string.enter_your_entry_time_hh_mm) },
                    dateButtonType = DateButtonType.TIME,
                    onClick = { showEnterTimePickerDialog = true },
                )

                Spacer(modifier = Modifier.height(8.dp))

                DateButton(
                    label = state.exitTimeInput.ifEmpty { stringResource(R.string.enter_your_exit_time_optional) },
                    dateButtonType = DateButtonType.TIME,
                    onClick = { showExitTimePickerDialog = true }
                )

                Spacer(modifier = Modifier.height(16.dp))

                state.totalTimeSpent.takeIf { it.isNotEmpty() }?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color(0xFF35BD69),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        text = it,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF35BD69),
                        textAlign = TextAlign.Center
                    )
                }

                state.exitTime.takeIf { it.isNotEmpty() }?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color(0xFF35BD69),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        text = stringResource(R.string.exit_time, state.exitTime),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF35BD69),
                        textAlign = TextAlign.Center
                    )
                }

                state.vacationMessage.takeIf { it.isNotEmpty() }?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.error,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        text = it,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { viewModel.onFabClicked() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = stringResource(R.string.clear)
            )
        }

        Button(
            onClick = { viewModel.clearEntries() },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.clear),
                fontFamily = FontFamily(Font(R.font.iran_sans_mobile_fa_num)),
            )
        }
    }

    if (showEnterTimePickerDialog) {
        TimePickerDialog(
            onConfirm = { timePickerState ->
                onTimeConfirm(
                    timePickerState,
                    viewModel::onEnterTimeChange
                ) { showEnterTimePickerDialog = it }
            },
            onDismiss = { showEnterTimePickerDialog = false }
        )
    }

    if (showExitTimePickerDialog) {
        TimePickerDialog(
            onConfirm = { timePickerState ->
                onTimeConfirm(
                    timePickerState,
                    viewModel::onExitTimeChange
                ) { showExitTimePickerDialog = it }
            },
            onDismiss = { showExitTimePickerDialog = false }
        )
    }

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.closeBottomSheet() },
            sheetState = bottomSheetState
        ) {
            CalculateRemainedDaysOff(onDismiss = { viewModel.closeBottomSheet() })
        }
    }
}

@LightAndDarkPreview
@Composable
fun PreviewExitTimeCalc(modifier: Modifier = Modifier) {
    KhoroojYarTheme {
        ExitTimeCalculatorScreen()
    }
}
