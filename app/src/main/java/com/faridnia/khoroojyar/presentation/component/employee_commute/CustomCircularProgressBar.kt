package com.faridnia.khoroojyar.presentation.component.employee_commute

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.component.CustomText
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.theme.Honeydew
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme
import com.faridnia.khoroojyar.presentation.theme.Ocean_Blue

@Composable
fun CustomCircularProgressBar(
    title: String,
    size: Dp,
    progress: Float // Progress as a percentage (0f to 100f)
) {
    val sweepAngle = (progress / 100f) * 360f // Convert percentage to degrees
    Column(
        modifier = Modifier
            .wrapContentSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(size)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Background circle
                    drawArc(
                        color = Honeydew,
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                    )
                    // Progress arc
                    drawArc(
                        color = Ocean_Blue,
                        startAngle = -90f, // Start from the top
                        sweepAngle = sweepAngle, // Calculate progress dynamically
                        useCenter = false,
                        style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_timer_start),
                    contentDescription = "Car icon",
                    tint = Color.Black,
                    modifier = Modifier.size(size / 2)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        CustomText(
            text = title,
            color = Color.Black,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2
        )
    }
}

@LightAndDarkPreview
@Composable
fun PreviewCustomProgressBard() {
    KhoroojYarTheme {
        CustomCircularProgressBar(
            title = "Hours Worked",
            size = 100.dp,
            progress = 66f
        )
    }
}