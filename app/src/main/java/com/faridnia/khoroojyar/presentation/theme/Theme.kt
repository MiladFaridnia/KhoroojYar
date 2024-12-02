package com.faridnia.khoroojyar.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = myDarkColorScheme()

private val LightColorScheme = myLightColorScheme()

fun myLightColorScheme(): ColorScheme = ColorScheme(
    primary = Caribbean_Green,
    onPrimary = Color.White,
    primaryContainer = Honeydew,
    onPrimaryContainer = Fence_Green,
    inversePrimary = Light_Blue,

    secondary = Light_Blue,
    onSecondary = Color.White,
    secondaryContainer = Ocean_Blue,
    onSecondaryContainer = Light_Blue,

    tertiary = Ocean_Blue,
    onTertiary = Color.White,
    tertiaryContainer = Honeydew,
    onTertiaryContainer = Ocean_Blue,

    background = Honeydew,
    onBackground = Fence_Green,

    surface = Color.White,
    onSurface = Fence_Green,
    surfaceVariant = Light_Green,
    onSurfaceVariant = Fence_Green,

    surfaceTint = Caribbean_Green,
    inverseSurface = Fence_Green,
    inverseOnSurface = Color.White,

    error = Color(0xFFB00020),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD4),
    onErrorContainer = Color(0xFF8C1D18),

    outline = Cyprus,
    outlineVariant = Light_Green,
    scrim = Color(0x1F000000),

    surfaceBright = Color.White,
    surfaceContainer = Honeydew,
    surfaceContainerHigh = Honeydew,
    surfaceContainerHighest = Honeydew,
    surfaceContainerLow = Color.White,
    surfaceContainerLowest = Honeydew,
    surfaceDim = Light_Green,
)


fun myDarkColorScheme(): ColorScheme = ColorScheme(
    primary = Caribbean_Green,
    onPrimary = Color.White,
    primaryContainer = Fence_Green,
    onPrimaryContainer = Caribbean_Green,
    inversePrimary = Light_Blue,

    secondary = Light_Blue,
    onSecondary = Color.White,
    secondaryContainer = Ocean_Blue,
    onSecondaryContainer = Light_Blue,

    tertiary = Ocean_Blue,
    onTertiary = Color.White,
    tertiaryContainer = Fence_Green,
    onTertiaryContainer = Ocean_Blue,

    background = Void,
    onBackground = Color.White,

    surface = Cyprus,
    onSurface = Color.White,
    surfaceVariant = Fence_Green,
    onSurfaceVariant = Color.White,

    surfaceTint = Caribbean_Green,
    inverseSurface = Color.White,
    inverseOnSurface = Void,

    error = Color(0xFFCF6679),
    onError = Color.White,
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFFFDAD4),

    outline = Fence_Green,
    outlineVariant = Void,
    scrim = Color(0x99000000),

    surfaceBright = Cyprus,
    surfaceContainer = Cyprus,
    surfaceContainerHigh = Void,
    surfaceContainerHighest = Void,
    surfaceContainerLow = Cyprus,
    surfaceContainerLowest = Void,
    surfaceDim = Void,
)


@Composable
fun KhoroojYarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
       /* dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }*/

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}