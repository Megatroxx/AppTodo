package com.example.apptodo.presentation.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.apptodo.presentation.AppInfoScreen
import com.example.apptodo.presentation.ItemListScreen
import com.example.apptodo.presentation.RedactorScreen
import com.example.apptodo.presentation.SettingsScreen
import com.example.apptodo.presentation.viewmodels.ItemListViewModel
import com.example.apptodo.presentation.viewmodels.RedactorViewModel
import com.example.apptodo.presentation.viewmodels.SettingsViewModel


/**
 * Composable function representing the main navigation structure of the application.
 *
 * @param navController The navigation controller that manages navigation within the app.
 * @param itemListViewModel The view model for the item list screen, providing data and logic related to the list of items.
 * @param redactorViewModel The view model for the redactor screen, providing data and logic for editing items.
 */

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AppScreen(
    navController: NavHostController
) {
    val itemListViewModel: ItemListViewModel = hiltViewModel()
    val redactorViewModel: RedactorViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "appScreen",
    ) {
        navigation(
            route = "appScreen",
            startDestination = DestinationEnum.LIST_SCREEN.destString
        ) {
            composable(DestinationEnum.LIST_SCREEN.destString) {
                ItemListScreen(
                    itemListViewModel = itemListViewModel,
                    redactorViewModel = redactorViewModel,
                    navController = navController
                )
            }

            composable(DestinationEnum.REDACTOR_SCREEN.destString) {
                RedactorScreen(navController = navController,
                    redactorViewModel = redactorViewModel
                )
            }
            composable(DestinationEnum.SETTINGS_SCREEN.destString) { backStackEntry ->
                val settingsViewModel: SettingsViewModel = hiltViewModel(backStackEntry)
                SettingsScreen(
                    settingsViewModel = settingsViewModel,
                    navController = navController
                )
            }
            composable(DestinationEnum.APP_INFO_SCREEN.destString){
                AppInfoScreen()
            }
        }
    }
}
