package com.faridnia.khoroojyar.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme
import java.time.LocalTime
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    time: LocalTime? = null,
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
    showSaveOption: Boolean = false,
    onSaveChecked: ((Boolean, TimePickerState) -> Unit)? = null
) {
    val currentTime = time ?: LocalTime.now()
    // State for the TimePicker
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.hour,
        initialMinute = currentTime.minute,
        is24Hour = true
    )

    // State for Save Checkbox (if applicable)
    var isSaveChecked by remember { mutableStateOf(false) }

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth()
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Time Picker
                TimePicker(
                    modifier = Modifier.fillMaxWidth(),
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        selectorColor = MaterialTheme.colorScheme.secondary
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Show Save Option if Enabled
                if (showSaveOption) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isSaveChecked,
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = MaterialTheme.colorScheme.onBackground,
                            ),
                            onCheckedChange = { isChecked ->
                                isSaveChecked = isChecked
                            }
                        )
                        Text(
                            text = stringResource(R.string.save),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Confirm Button
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onConfirm(timePickerState)
                        if (showSaveOption)
                            onSaveChecked?.invoke(isSaveChecked, timePickerState)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.confirm),
                        fontFamily = FontFamily(Font(R.font.iran_sans_mobile_fa_num)),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@LightAndDarkPreview
@Composable
private fun TimePickerDialogPreview() {
    KhoroojYarTheme {
        TimePickerDialog(
            onConfirm = {},
            onDismiss = {}
        )
    }
}