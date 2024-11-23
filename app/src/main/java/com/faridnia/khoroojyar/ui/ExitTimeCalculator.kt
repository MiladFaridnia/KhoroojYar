package com.faridnia.khoroojyar.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.ui.component.TimePickerDialog
import com.faridnia.khoroojyar.ui.theme.KhoroojYarTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExitTimeCalculator(viewModel: ExitTimeViewModel = viewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current

    var showEnterTimePickerDialog by remember { mutableStateOf(false) }
    var showExitTimePickerDialog by remember { mutableStateOf(false) }

    val onTimeConfirm: (TimePickerState, (String) -> Unit, (Boolean) -> Unit) -> Unit =
        { timePickerState, onTimeChange, closeDialog ->
            val selectedTime =
                String.format(Locale.US, "%02d:%02d", timePickerState.hour, timePickerState.minute)
            onTimeChange(selectedTime)
            closeDialog(false)
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimePickerButton(
            label = state.enterTimeInput.ifEmpty { stringResource(R.string.enter_your_entry_time_hh_mm) },
            onClick = { showEnterTimePickerDialog = true }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TimePickerButton(
            label = state.exitTimeInput.ifEmpty { stringResource(R.string.enter_your_exit_time_optional) },
            onClick = { showExitTimePickerDialog = true }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                keyboardController?.hide()
                viewModel.calculateTime()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.calculate_exit_time))
        }

        Spacer(modifier = Modifier.height(16.dp))

        state.exitTime.takeIf { it.isNotEmpty() }?.let {
            Text(
                text = stringResource(R.string.exit_time, state.exitTime),
                style = MaterialTheme.typography.titleLarge
            )
        }

        state.vacationMessage.takeIf { it.isNotEmpty() }?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
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
}

@Composable
fun TimePickerButton(label: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = label)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewExitTimeCalc(modifier: Modifier = Modifier) {
    KhoroojYarTheme {
        ExitTimeCalculator()
    }
}
