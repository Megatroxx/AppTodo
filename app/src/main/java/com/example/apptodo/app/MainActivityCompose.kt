package com.example.apptodo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.apptodo.compose_ui.ui.theme.ToDoAppTheme
import com.example.apptodo.navigation.AppScreen
import com.example.apptodo.viewmodels.ItemListViewModel
import com.example.apptodo.viewmodels.RedactorViewModel

class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val itemListFactory = (application as App).itemListViewModelFactory
        val itemListViewModel =
            ViewModelProvider(this, itemListFactory)[ItemListViewModel::class.java]

        val redactorFactory = (application as App).redactorViewModelFactory
        val redactorViewModel =
            ViewModelProvider(this, redactorFactory)[RedactorViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController: NavHostController = rememberNavController()
                    AppScreen(
                        itemListViewModel = itemListViewModel,
                        redactorViewModel = redactorViewModel,
                        navController = navController
                    )
                }
            }

        }
    }
}
