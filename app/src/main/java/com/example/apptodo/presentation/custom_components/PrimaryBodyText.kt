package com.example.apptodo.presentation.custom_components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


/**
 * Composable function that displays primary body text with specified text, style, and color.
 *
 * @param text The text to display.
 * @param modifier The modifier to apply to this layout node.
 */

@Composable
fun PrimaryBodyText(text: String, modifier: Modifier){
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onPrimary
    )
}