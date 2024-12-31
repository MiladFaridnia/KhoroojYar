package com.faridnia.khoroojyar.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = myDarkColorScheme()

private val LightColorScheme = myLightColorScheme()

fun myLightColorScheme(): ColorScheme = ColorScheme(
    primary = Caribbean_Green,
    onPrimary = Fence_Green,
    primaryContainer = Light_Green,
    onPrimaryContainer = Fence_Green,
    inversePrimary = Light_Blue,

    secondary = Light_Blue,
    onSecondary = Light_Green,
    secondaryContainer = Ocean_Blue,
    onSecondaryContainer = Light_Blue,

    tertiary = Ocean_Blue,
    onTertiary = Light_Green,
    tertiaryContainer = Honeydew,
    onTertiaryContainer = Ocean_Blue,

    background = Caribbean_Green,
    onBackground = Fence_Green,

    surface = Light_Green,
    onSurface = Fence_Green,
    surfaceVariant = Light_Green,
    onSurfaceVariant = Fence_Green,

    surfaceTint = Caribbean_Green,
    inverseSurface = Fence_Green,
    inverseOnSurface = Light_Green,

    error = Color(0xFFB00020),
    onError = Light_Green,
    errorContainer = Color(0xFFFFDAD4),
    onErrorContainer = Color(0xFF8C1D18),

    outline = Cyprus,
    outlineVariant = Light_Green,
    scrim = Color(0x1F000000),

    surfaceBright = Honeydew,
    surfaceContainer = Honeydew,
    surfaceContainerHigh = Light_Green,
    surfaceContainerHighest = Light_Green,
    surfaceContainerLow = Cyprus,
    surfaceContainerLowest = Fence_Green,
    surfaceDim = Light_Green,
)

fun myDarkColorScheme(): ColorScheme = ColorScheme(
    primary = Caribbean_Green,
    onPrimary = Fence_Green, // Updated onPrimary color
    primaryContainer = Fence_Green,
    onPrimaryContainer = Caribbean_Green,
    inversePrimary = Light_Blue,

    secondary = Light_Blue,
    onSecondary = Light_Green,
    secondaryContainer = Ocean_Blue,
    onSecondaryContainer = Light_Blue,

    tertiary = Ocean_Blue,
    onTertiary = Light_Green,
    tertiaryContainer = Fence_Green,
    onTertiaryContainer = Ocean_Blue,

    background = Void_Color,
    onBackground = Light_Green,

    surface = Cyprus,
    onSurface = Light_Green,
    surfaceVariant = Fence_Green,
    onSurfaceVariant = Light_Green,

    surfaceTint = Caribbean_Green,
    inverseSurface = Light_Green,
    inverseOnSurface = Void_Color,

    error = Color(0xFFCF6679),
    onError = Light_Green,
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFFFDAD4),

    outline = Fence_Green,
    outlineVariant = Void_Color,
    scrim = Color(0x99000000),

    surfaceBright = Void_Color,
    surfaceContainer = Void_Color,
    surfaceContainerHigh = Fence_Green,
    surfaceContainerHighest = Caribbean_Green,
    surfaceContainerLow = Light_Green,
    surfaceContainerLowest = Honeydew,
    surfaceDim = Void_Color,
)


@Composable
fun KhoroojYarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}