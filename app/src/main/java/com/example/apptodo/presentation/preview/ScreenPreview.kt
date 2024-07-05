package com.example.apptodo.presentation.preview

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.apptodo.data.database.AppDatabase
import com.example.apptodo.data.repository.TodoItemsRepository
import com.example.apptodo.presentation.ItemListScreen
import com.example.apptodo.presentation.RedactorScreen
import com.example.apptodo.presentation.ui.theme.ToDoAppTheme
import com.example.apptodo.presentation.viewmodels.ItemListViewModel
import com.example.apptodo.presentation.viewmodels.RedactorViewModel


@Preview(showBackground = true)
@Composable
private fun PreviewItemListScreen() {
/*    val navController = rememberNavController()
    val itemListViewModel = ItemListViewModel(TodoItemsRepository(AppDatabase.getDatabase(
        LocalContext.current).todoDao()))
    val redactorViewModel = RedactorViewModel(TodoItemsRepository(AppDatabase.getDatabase(
        LocalContext.current).todoDao()))



    ToDoAppTheme {
        ItemListScreen(
            navController = navController,
            itemListViewModel = itemListViewModel,
            redactorViewModel = redactorViewModel
        )
    }*/
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDarkItemListScreen() {
/*    val navController = rememberNavController()
    val itemListViewModel = ItemListViewModel(TodoItemsRepository(AppDatabase.getDatabase(
        LocalContext.current).todoDao()))
    val redactorViewModel = RedactorViewModel(TodoItemsRepository(AppDatabase.getDatabase(
        LocalContext.current).todoDao()))


    ToDoAppTheme {
        ItemListScreen(
            navController = navController,
            itemListViewModel = itemListViewModel,
            redactorViewModel = redactorViewModel
        )
    }*/
}

@Preview(showBackground = true)
@Composable
private fun PreviewRedactorScreen() {
/*    val navController = rememberNavController()
    val redactorViewModel = RedactorViewModel(TodoItemsRepository(AppDatabase.getDatabase(
        LocalContext.current).todoDao()))


    ToDoAppTheme {
        RedactorScreen(navController = navController, redactorViewModel = redactorViewModel)
    }*/
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewDarkRedactorScreen() {
/*    val navController = rememberNavController()
    val redactorViewModel = RedactorViewModel(TodoItemsRepository(AppDatabase.getDatabase(
        LocalContext.current).todoDao()))


    ToDoAppTheme {
        RedactorScreen(navController = navController, redactorViewModel = redactorViewModel)
    }*/
}