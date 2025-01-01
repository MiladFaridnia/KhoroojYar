package com.faridnia.khoroojyar.presentation.analitics

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.faridnia.khoroojyar.presentation.component.CustomBox
import com.faridnia.khoroojyar.presentation.component.CustomCard
import com.faridnia.khoroojyar.presentation.component.ExtraRoundCard
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.component.bottom_navigation.BottomNavigationBar
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
    val chartData by viewModel.chartData.collectAsStateWithLifecycle()
    CustomScaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        WorkTimeChart(state = chartData, onEvent = viewModel::onEvent)
    }
}

@Composable
fun WorkTimeChart(
    state: List<Float>,
    onEvent: (WorkDayChartEvent) -> Unit
) {
    val columnChart = columnChart()
    val lineChart = lineChart()

    val composedChartEntryModelProducer = ComposedChartEntryModelProducer.build {
        add(entriesOf(*state.mapIndexed { index, value -> index.toFloat() to value }
            .toTypedArray()))
        add(entriesOf(*state.mapIndexed { index, value -> index.toFloat() to value }
            .toTypedArray()))
    }

    val scrollState = rememberScrollState()

    CustomBox(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.scrollable(scrollState, orientation = Orientation.Vertical)) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 30.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


            }

            ExtraRoundCard(
                modifier = Modifier.weight(1f)
            ) {
                ProvideChartStyle(myChartStyle()) {
                    CustomCard(modifier = Modifier.padding(16.dp)) {
                        Chart(
                            modifier = Modifier
                                .background(Honeydew)
                                .padding(6.dp),
                            chart = remember(columnChart, lineChart) { columnChart + lineChart },
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
        }
    }
}


@LightAndDarkPreview
@Composable
fun PreviewWorkTimeChart() {
    KhoroojYarTheme {
        WorkTimeChart(listOf(4.0f, 8.4f, 9.0f, 11.0f, 8.45f, 10.0f)) {}
    }
}