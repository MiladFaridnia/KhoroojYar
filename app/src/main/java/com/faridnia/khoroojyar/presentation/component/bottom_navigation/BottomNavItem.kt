package com.faridnia.khoroojyar.presentation.component.bottom_navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.faridnia.khoroojyar.R
import com.faridnia.khoroojyar.presentation.util.Screen

sealed class BottomNavItem(
    @StringRes val titleId: Int,
    val selectedIcon: NavIcon? = null,
    val unselectedIcon: NavIcon? = null,
    var selected: Boolean = false,
    val route: String
) {
    val icon: NavIcon?
        get() = if (selected) selectedIcon else unselectedIcon

    data object Home : BottomNavItem(
        titleId = R.string.home,
        selectedIcon = NavIcon.Vector(Icons.Filled.Home),
        unselectedIcon = NavIcon.Vector(Icons.Outlined.Home),
        selected = true,
        route = Screen.ExitTimeCalculatorScreen.route
    )

    data object Analytics : BottomNavItem(
        titleId = R.string.analytics,
        selectedIcon = NavIcon.PainterId(R.drawable.ic_cube_fill),
        unselectedIcon = NavIcon.PainterId(R.drawable.ic_cube),
        route = Screen.Analytics.route
    )

    data object Settings : BottomNavItem(
        titleId = R.string.settings,
        selectedIcon = NavIcon.PainterId(R.drawable.ic_setting_fill),
        unselectedIcon = NavIcon.PainterId(R.drawable.ic_setting),
        route = Screen.Settings.route
    )
}

sealed class NavIcon {
    data class Vector(val icon: ImageVector) : NavIcon()
    data class PainterId(@DrawableRes val iconId: Int) : NavIcon()
}