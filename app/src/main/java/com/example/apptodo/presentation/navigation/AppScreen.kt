package com.example.apptodo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.apptodo.presentation.ItemListScreen
import com.example.apptodo.presentation.RedactorScreen
import com.example.apptodo.presentation.viewmodels.ItemListViewModel
import com.example.apptodo.presentation.viewmodels.RedactorViewModel


/**
 * Composable function representing the main navigation structure of the application.
 *
 * @param navController The navigation controller that manages navigation within the app.
 * @param itemListViewModel The view model for the item list screen, providing data and logic related to the list of items.
 * @param redactorViewModel The view model for the redactor screen, providing data and logic for editing items.
 */


@Composable
fun AppScreen(
    navController: NavHostController,
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

        }
    }
}
