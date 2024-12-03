package com.faridnia.khoroojyar.presentation.component.employee_commute

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.component.CustomCard
import com.faridnia.khoroojyar.presentation.component.LightAndDarkPreview
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme

@Composable
fun InOutHorizontalCard(
    @DrawableRes icon: Int,
    title: String,
    detail: String,
) {
    CustomCard(
        modifier = Modifier
            .padding(8.dp)
            .wrapContentWidth()
    ) {
        InOutHorizontalComponent(icon, title, detail)
    }
}


@LightAndDarkPreview
@Composable
fun PreviewInOutHorizontalCard(modifier: Modifier = Modifier) {
    KhoroojYarTheme {
        InOutHorizontalCard(
            title = "Expense",
            detail = "$1,187.40",
            icon = R.drawable.ic_out
        )
    }
}