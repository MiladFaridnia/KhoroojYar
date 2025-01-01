package com.faridnia.khoroojyar.presentation.analitics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.calculate_days_off.WorkDayInfoChartState
import com.faridnia.khoroojyar.presentation.component.CustomBox
import com.faridnia.khoroojyar.presentation.component.CustomCard
import com.faridnia.khoroojyar.presentation.component.ExtraRoundCard
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.component.bottom_navigation.BottomNavigationBar
import com.faridnia.khoroojyar.presentation.component.employee_commute.InOutVerticalComponent
import com.faridnia.khoroojyar.presentation.component.home.WorkingHourItem
import com.faridnia.khoroojyar.presentation.theme.Honeydew
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme
import com.faridnia.khoroojyar.presentation.theme.Vivid_Blue
import com.jrg.app.ui.component.snackbar.CustomScaffold
import com.patrykandpatrick.vico.compose.axis.axisGuidelineComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shape.dashedShape
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.chart.composed.plus
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.composed.ComposedChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entriesOf

@Composable
fun WorkDayChartScreen(
    navController: NavController,
    viewModel: WorkDayInfoViewModel = hiltViewModel()
) {
    val chartData by viewModel.state.collectAsStateWithLifecycle()
    CustomScaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        WorkTimeChart(state = chartData, onEvent = viewModel::onEvent)
    }
}

@Composable
fun WorkTimeChart(
    state: WorkDayInfoChartState,
    onEvent: (WorkDayChartEvent) -> Unit
) {
    val columnChart = columnChart()
    val lineChart = lineChart()

    val composedChartEntryModelProducer = ComposedChartEntryModelProducer.build {
        add(entriesOf(*state.workedTimeList.mapIndexed { index, value -> index.toFloat() to value }
            .toTypedArray()))
        add(entriesOf(*state.workedTimeList.mapIndexed { index, value -> index.toFloat() to value }
            .toTypedArray()))
    }

    val scrollState = rememberScrollState()

    CustomBox(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    InOutVerticalComponent(
                        title = "Expense",
                        detail = "$1,187.40",
                        icon = R.drawable.ic_in
                    )

                    VerticalDivider(
                        modifier = Modifier
                            .height(60.dp)
                            .width(1.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    InOutVerticalComponent(
                        title = "Income",
                        detail = "$2,500.00",
                        icon = R.drawable.ic_in
                    )

                    VerticalDivider(
                        modifier = Modifier
                            .height(60.dp)
                            .width(1.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    InOutVerticalComponent(
                        title = "Balance",
                        detail = "$1,312.60",
                        icon = R.drawable.ic_out
                    )
                }
            }

            ExtraRoundCard {
                ProvideChartStyle(myChartStyle()) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CustomCard(modifier = Modifier.padding(horizontal = 24.dp)) {
                            Chart(
                                modifier = Modifier
                                    .background(Honeydew)
                                    .padding(6.dp),
                                chart = remember(columnChart, lineChart) {
                                    columnChart + lineChart
                                },
                                chartModelProducer = composedChartEntryModelProducer,
                                startAxis = rememberStartAxis(
                                    axis = null,
                                    guideline = axisGuidelineComponent(
                                        color = Vivid_Blue,
                                        thickness = 0.5.dp,
                                        shape = Shapes.dashedShape(
                                            shape = Shapes.rectShape,
                                            dashLength = 2.dp,
                                            gapLength = 1.dp,
                                        )
                                    )
                                ),
                                bottomAxis = rememberBottomAxis(guideline = null),
                            )
                        }

                        CustomCard(modifier = Modifier.padding(horizontal = 24.dp)) {
                            Chart(
                                modifier = Modifier
                                    .background(Honeydew)
                                    .padding(6.dp),
                                chart = remember(columnChart, lineChart) {
                                    columnChart + lineChart
                                },
                                chartModelProducer = composedChartEntryModelProducer,
                                startAxis = rememberStartAxis(
                                    axis = null,
                                    guideline = axisGuidelineComponent(
                                        color = Vivid_Blue,
                                        thickness = 0.5.dp,
                                        shape = Shapes.dashedShape(
                                            shape = Shapes.rectShape,
                                            dashLength = 2.dp,
                                            gapLength = 1.dp,
                                        )
                                    )
                                ),
                                bottomAxis = rememberBottomAxis(guideline = null),
                            )
                        }
                    }
                }

                state.remainedTimeOff.let {
                    WorkingHourItem(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        title = "Time Worked",
                        time = it.toString(),
                        timeDuration = it.toString(),
                        iconId = R.drawable.ic_timer
                    )

                    WorkingHourItem(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        title = "Time Worked",
                        time = it.toString(),
                        timeDuration = it.toString(),
                        iconId = R.drawable.ic_timer
                    )
                }
            }
        }
    }
}

@LightAndDarkPreview
@Composable
fun PreviewWorkTimeChart() {
    KhoroojYarTheme {
        WorkTimeChart(
            state = WorkDayInfoChartState(listOf(4.0f, 8.4f, 9.0f, 11.0f, 8.45f, 10.0f)),
            onEvent = {})
    }
}