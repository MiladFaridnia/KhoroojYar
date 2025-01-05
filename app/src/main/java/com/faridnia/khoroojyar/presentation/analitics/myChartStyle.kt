package com.faridnia.khoroojyar.presentation.analitics

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.faridnia.khoroojyar.presentation.theme.Caribbean_Green
import com.faridnia.khoroojyar.presentation.theme.Cyprus
import com.faridnia.khoroojyar.presentation.theme.Light_Blue
import com.faridnia.khoroojyar.presentation.theme.Ocean_Blue
import com.faridnia.khoroojyar.presentation.theme.Vivid_Blue
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent

@Composable
fun myChartStyle(
    axisLabelColor: Color = Caribbean_Green,
    axisGuidelineColor: Color = Cyprus,
    axisLineColor: Color = Ocean_Blue,
    entityColors: List<Color> = listOf(
        Vivid_Blue,
        Caribbean_Green,
        Light_Blue
    ),
    elevationOverlayColor: Color = colorScheme.primary,
): ChartStyle = remember(
    axisLabelColor,
    axisGuidelineColor,
    axisLineColor,
    entityColors,
    elevationOverlayColor,
) {
    ChartStyle(
        axis = ChartStyle.Axis(
            axisLabelColor = axisLabelColor,
            axisGuidelineColor = axisGuidelineColor,
            axisLineColor = axisLineColor,
        ),
        columnChart = ChartStyle.ColumnChart(
              columns = entityColors.map { color ->
                  LineComponent(
                      color = color.toArgb(),
                      thicknessDp = 16f,
                  )
              }
        ),
        lineChart = ChartStyle.LineChart(
            lines = entityColors.map { color ->
                LineChart.LineSpec(
                    lineColor = color.toArgb(),
                )
            }
        ),
        marker = ChartStyle.Marker(),
        elevationOverlayColor = elevationOverlayColor,
    )
}
