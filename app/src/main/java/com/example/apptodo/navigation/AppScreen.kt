package com.example.apptodo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.apptodo.compose_ui.ItemListScreen
import com.example.apptodo.compose_ui.RedactorScreen
import com.example.apptodo.viewmodels.ItemListViewModel
import com.example.apptodo.viewmodels.RedactorViewModel

@Composable
fun AppScreen(
    navController: NavHostController,
    itemListViewModel : ItemListViewModel,
    redactorViewModel : RedactorViewModel

) {
    NavHost(
        navController = navController,
        startDestination = "appScreen",
    ){
        navigation(
            route = "appScreen",
            startDestination = DestinationEnum.LIST_SCREEN.destString
        ) {
            composable(DestinationEnum.LIST_SCREEN.destString) {
                ItemListScreen(itemListViewModel = itemListViewModel, redactorViewModel = redactorViewModel, navController = navController)
            }

            composable(DestinationEnum.REDACTOR_SCREEN.destString) {
                RedactorScreen(navController = navController, redactorViewModel = redactorViewModel)
            }

        }
    }
}
