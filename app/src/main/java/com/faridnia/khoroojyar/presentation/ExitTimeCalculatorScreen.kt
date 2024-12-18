package com.faridnia.khoroojyar.presentation

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.calculate_days_off.CalculateRemainedDaysOff
import com.faridnia.khoroojyar.presentation.component.CustomBox
import com.faridnia.khoroojyar.presentation.component.CustomText
import com.faridnia.khoroojyar.presentation.component.ExtraRoundCard
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.component.TimePickerDialog
import com.faridnia.khoroojyar.presentation.component.employee_commute.EmployeeCommute
import com.faridnia.khoroojyar.presentation.component.home.DateItem
import com.faridnia.khoroojyar.presentation.component.home.DateTimeHeader
import com.faridnia.khoroojyar.presentation.component.home.GreetingWithIcon
import com.faridnia.khoroojyar.presentation.component.home.ProgressBarWithMessage
import com.faridnia.khoroojyar.presentation.component.home.WorkingHourItem
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme
import com.faridnia.khoroojyar.util.DateHelper
import com.razaghimahdi.compose_persian_date.core.PersianDatePickerController
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExitTimeCalculatorScreen(viewModel: ExitTimeViewModel = viewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var showEnterTimePickerDialog by remember { mutableStateOf(false) }
    var showExitTimePickerDialog by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    val dayOfWeek by remember { mutableStateOf(DateHelper().getDayOfWeek()) }
    val dayOfMonth by remember { mutableStateOf(DateHelper().getCurrentGregorianDayInMonth()) }
    val persianMonthName by remember { mutableStateOf(PersianDatePickerController().getPersianMonthName()) }
    val persianDay by remember {
        mutableStateOf(
            PersianDatePickerController().getPersianDay().toString()
        )
    }

    val monthPercentage by remember {
        mutableIntStateOf(
            ((PersianDatePickerController().getPersianDay().toLong() * 100 / 30L)).toInt()
        )
    }

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

    val scrollState = rememberScrollState()

    CustomBox(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(Modifier.scrollable(scrollState, orientation = Orientation.Vertical)) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 30.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                GreetingWithIcon(
                    greetingText = "Hi Agha Milad",
                    subText = "Good Morning",
                    icon = painterResource(R.drawable.ic_sun)
                )

                DateTimeHeader(
                    firstDate = DateItem(dayOfMonth, dayOfWeek),
                    secondDate = DateItem(persianMonthName ?: "", persianDay)
                )

                Spacer(modifier = Modifier.height(12.dp))

                ProgressBarWithMessage(
                    percentage = monthPercentage,
                    amount = "0f 30 days",
                    percentageTitle = "$persianDay days "
                )

            }

            ExtraRoundCard(
                modifier = Modifier.weight(1f)
            ) {

                EmployeeCommute(
                    Modifier
                        .wrapContentSize()
                        .padding(horizontal = 16.dp),
                    enterLabel = state.enterTimeInput.ifEmpty { stringResource(R.string.enter_your_entry_time_hh_mm) },
                    exitLabel = state.exitTimeInput.ifEmpty { stringResource(R.string.enter_your_exit_time_optional) },
                    enterOnClick = { showEnterTimePickerDialog = true },
                    exitOnClick = { showExitTimePickerDialog = true }
                )

                ElevatedCard(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.elevatedCardColors().copy(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CustomText(
                            text = "Working Hours Summary",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {

                    state.timeWorked?.let {
                        item {
                            WorkingHourItem(
                                title = "Time Worked",
                                time = it.startToEndTime,
                                timeDuration = it.duration,
                                iconId = R.drawable.ic_timer
                            )
                        }
                    }

                    state.overtime?.let {
                        item {
                            WorkingHourItem(
                                title = "Overtime",
                                time = it.startToEndTime,
                                timeDuration = it.duration,
                                iconId = R.drawable.ic_time_stop
                            )
                        }
                    }

                    state.exitTime.takeIf { it.isNotEmpty() }?.let { exitTime ->
                        item {
                            WorkingHourItem(
                                title = "Exit Time",
                                timeDuration = exitTime,
                                iconId = R.drawable.ic_exiting_employee
                            )
                        }
                    }

                    state.vacationList.takeIf { it.isNotEmpty() }?.let { vacationList ->
                        val ordinalSuffixes = listOf(
                            "First",
                            "Second",
                            "Third",
                            "Fourth",
                            "Fifth",
                            "Sixth",
                            "Seventh",
                            "Eighth",
                            "Ninth",
                            "Tenth"
                        )

                        itemsIndexed(vacationList) { index: Int, item: TimeSegment ->
                            val title = when (vacationList.size) {
                                1 -> "Time Off"
                                else -> "${ordinalSuffixes.getOrElse(index) { "${index + 1}th" }} Time Off"
                            }

                            WorkingHourItem(
                                title = title,
                                time = item.startToEndTime,
                                timeDuration = item.duration,
                                iconId = R.drawable.ic_timer_pause
                            )
                        }
                    }
                }

                /* DateButton(
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

                 Spacer(modifier = Modifier.height(16.dp))*/

                /*      FloatingActionButton(
                         onClick = { viewModel.onFabClicked() },
                         modifier = Modifier
                             //  .align(Alignment.BottomEnd)
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
                                     /// .align(Alignment.BottomStart)
                                     .padding(16.dp)
                             ) {
                                 Text(
                                     text = stringResource(R.string.clear),
                                     fontFamily = FontFamily(Font(R.font.iran_sans_mobile_fa_num)),
                                 )
                             }
             */
            }
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
