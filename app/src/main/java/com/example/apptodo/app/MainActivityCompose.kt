package com.example.apptodo.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.apptodo.presentation.ui.theme.ToDoAppTheme
import com.example.apptodo.presentation.navigation.AppScreen
import com.example.apptodo.presentation.ui.theme.LocalThemeOption
import com.example.apptodo.presentation.viewmodels.ItemListViewModel
import com.example.apptodo.presentation.viewmodels.RedactorViewModel
import com.example.apptodo.presentation.viewmodels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * MainActivityCompose serves as the entry point for the application using Jetpack Compose.
 * It initializes key components such as ViewModels for the task list and editor,
 * and configures the interface using Jetpack Compose components.
 */

@AndroidEntryPoint
class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val selectedTheme by settingsViewModel.selectedTheme.collectAsState()

            CompositionLocalProvider(LocalThemeOption provides selectedTheme) {
                ToDoAppTheme {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surface),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        val navController: NavHostController = rememberNavController()
                        AppScreen(navController = navController)
                    }
                }
            }
        }
    }
}
