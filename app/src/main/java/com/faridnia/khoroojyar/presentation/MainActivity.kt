package com.faridnia.khoroojyar.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.faridnia.khoroojyar.presentation.analitics.WorkDayChartScreen
import com.faridnia.khoroojyar.presentation.exit_time_calculator.ExitTimeCalculatorScreen
import com.faridnia.khoroojyar.presentation.profile.ProfileScreen
import com.faridnia.khoroojyar.presentation.settings.SettingsScreen
import com.faridnia.khoroojyar.presentation.theme.KhoroojYarTheme
import com.faridnia.khoroojyar.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val navController = rememberNavController()
            KhoroojYarTheme(
                darkTheme = viewModel.isDark.collectAsStateWithLifecycle().value
                    ?: isSystemInDarkTheme()
            ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ExitTimeCalculatorScreen.route
                    ) {
                        composable(Screen.ExitTimeCalculatorScreen.route) {
                            ExitTimeCalculatorScreen(navController = navController)
                        }
                        composable(Screen.Settings.route) {
                            SettingsScreen(navController = navController)
                        }
                        composable(Screen.Analytics.route) {
                            WorkDayChartScreen(navController)
                        }
                        composable(Screen.Profile.route) {
                            ProfileScreen(navController)
                        }
                    }
            }
        }
    }
}