package com.faridnia.khoroojyar.presentation.component.employee_commute

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun EmployeeCommute(
    modifier: Modifier = Modifier,
    enterLabel: String,
    exitLabel: String,
    enterOnClick: () -> Unit,
    exitOnClick: () -> Unit
) {
    ElevatedCard(
        shape = RoundedCornerShape(30.dp),
        modifier = modifier
            .wrapContentSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomCircularProgressBar(
                title = "Hours Worked",
                progress = 68f,
                size = 100.dp
            )

            VerticalDivider(
                modifier = Modifier
                    .heightIn(min = 80.dp, max = 100.dp)
                    .padding(vertical = 16.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                InOutHorizontalComponent(
                    title = "Enter Time",
                    detail = enterLabel,
                    icon = R.drawable.ic_in,
                    onClick = enterOnClick
                )

                HorizontalDivider(
                    thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface
                )

                InOutHorizontalComponent(
                    icon = R.drawable.ic_out,
                    title = "Exit Time",
                    detail = exitLabel,
                    onClick = exitOnClick
                )
            }
        }
    }
}

@LightAndDarkPreview
@Composable
fun PreviewEmployeeCommute(modifier: Modifier = Modifier) {
    KhoroojYarTheme {
        EmployeeCommute(
            Modifier.wrapContentSize(),
            enterLabel = "00:00",
            exitLabel = "18:00",
            enterOnClick = { },
            exitOnClick = {}
        )
    }
}