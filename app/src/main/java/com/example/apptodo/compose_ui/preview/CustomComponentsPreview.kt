package com.example.apptodo.compose_ui.preview

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apptodo.compose_ui.custom_components.Divider
import com.example.apptodo.compose_ui.custom_components.PrimaryBodyText
import com.example.apptodo.compose_ui.custom_components.Shadow
import com.example.apptodo.compose_ui.ui.theme.ToDoAppTheme
import com.example.apptodo.data.TodoItem
import com.example.apptodo.utils.Relevance
import com.example.apptodo.utils.FakeTodoItem


@Preview(showBackground = true)
@Composable
fun PreviewTodoItem() {

    val todoItem = TodoItem(
        "10",
        "Купить что-то",
        Relevance.URGENT,
        "2024-01-01",
        false,
        "2023-12-11",
        null
    )

    ToDoAppTheme {
        FakeTodoItem(todoItem = todoItem) {
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DarkPreviewTodoItem() {

    val todoItem = TodoItem(
        "10",
        "Купить что-то",
        Relevance.URGENT,
        "2024-01-01",
        true,
        "2023-12-11",
        null
    )

    ToDoAppTheme {
        FakeTodoItem(todoItem = todoItem) {
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMyShadow() {
    //Отображается странно, но я хз, как по другому тень показать:)
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.White)
            .padding(16.dp)
            .Shadow(
                color = Color.Gray,
                borderRadius = 12.dp,
                blurRadius = 8.dp,
                offsetY = 4.dp,
                spread = 2.dp
            )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPrimaryBodyText() {
    ToDoAppTheme {
        Box(
            modifier = Modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            PrimaryBodyText(
                text = "Пример текста",
                modifier = Modifier
                    .padding(8.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewDivider() {
    ToDoAppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Above the divider")
            Divider(modifier = Modifier.padding(8.dp))
            Text("Below the divider")
        }
    }
}



