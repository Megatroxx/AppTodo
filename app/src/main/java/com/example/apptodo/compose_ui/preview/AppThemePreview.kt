package com.example.apptodo.compose_ui.preview

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apptodo.compose_ui.ui.theme.ToDoAppTheme

@Composable
fun ThemePreview() {
    ToDoAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            val colorPairs = listOf(
                "Primary" to MaterialTheme.colorScheme.primary,
                "Secondary" to MaterialTheme.colorScheme.secondary,
                "Tertiary" to MaterialTheme.colorScheme.tertiary,
                "Error" to MaterialTheme.colorScheme.error,
                "Scrim" to MaterialTheme.colorScheme.scrim,
                "Outline" to MaterialTheme.colorScheme.outline,
                "Surface" to MaterialTheme.colorScheme.surface,
                "Background" to MaterialTheme.colorScheme.background,
                "OnPrimary" to MaterialTheme.colorScheme.onPrimary,
                "OnSecondary" to MaterialTheme.colorScheme.onSecondary,
                "OnTertiary" to MaterialTheme.colorScheme.onTertiary,
                "OnSurfaceVariant" to MaterialTheme.colorScheme.onSurfaceVariant
            )

            colorPairs.forEach { (name, color) ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(color)
                        .padding(8.dp)
                ) {
                    Text(
                        text = name,
                        color = if (color.luminance() > 0.5) Color.Black else Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            val typographyStyles = listOf(
                "TitleLarge" to MaterialTheme.typography.titleLarge,
                "TitleMedium" to MaterialTheme.typography.titleMedium,
                "TitleSmall" to MaterialTheme.typography.titleSmall,
                "BodyMedium" to MaterialTheme.typography.bodyMedium,
                "BodySmall" to MaterialTheme.typography.bodySmall
            )

            typographyStyles.forEach { (name, textStyle) ->
                Text(
                    text = name,
                    style = textStyle,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewThemePreview() {
    ThemePreview()
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewDarkThemePreview() {
    ThemePreview()
}