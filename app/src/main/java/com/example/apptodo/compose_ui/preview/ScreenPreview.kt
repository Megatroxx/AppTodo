package com.example.apptodo.compose_ui.preview

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.apptodo.compose_ui.ui.theme.ToDoAppTheme
import com.example.apptodo.utils.ItemListScreen
import com.example.apptodo.utils.RedactorScreen


@Preview(showBackground = true)
@Composable
fun PreviewItemListScreen() {
    val navController = rememberNavController()

    ToDoAppTheme {
        ItemListScreen(navController = navController)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DarkPreviewItemListScreen() {
    val navController = rememberNavController()

    ToDoAppTheme {
        ItemListScreen(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRedactorScreen() {
    val navController = rememberNavController()

    ToDoAppTheme {
        RedactorScreen(navController = navController)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DarkPreviewRedactorScreen() {
    val navController = rememberNavController()

    ToDoAppTheme {
        RedactorScreen(navController = navController)
    }
}