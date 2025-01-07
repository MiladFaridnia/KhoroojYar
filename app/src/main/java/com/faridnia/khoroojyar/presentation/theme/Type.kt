package com.faridnia.khoroojyar.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.faridnia.khoroojyar.R

val Typography = Typography(
    displayLarge = TextStyle(
        fontSize = 34.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(Font(R.font.noto_sans_arabic_condensed_light, FontWeight.Light))
    ),
    displayMedium = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily(Font(R.font.noto_sans_arabic_condensed_light, FontWeight.Light))
    ),
    headlineSmall = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily(Font(R.font.noto_sans_arabic_condensed_light, FontWeight.Light))
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily(Font(R.font.noto_sans_arabic_condensed_light, FontWeight.Light))
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily(Font(R.font.noto_sans_arabic_condensed_light, FontWeight.Light))
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily(Font(R.font.noto_sans_arabic_condensed_light, FontWeight.Light)),
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(Font(R.font.noto_sans_arabic_condensed_light, FontWeight.Light)),
    ),
    titleSmall = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily(Font(R.font.noto_sans_arabic_condensed_light, FontWeight.Light)),
    )
)
