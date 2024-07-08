package com.example.apptodo.presentation.custom_components

import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


/**
 * Composable function that displays a divider with specified color and thickness.
 *
 * @param modifier The modifier to apply to this layout node.
 */

@Composable
fun Divider(modifier: Modifier){
    Divider(
        color = MaterialTheme.colorScheme.outlineVariant,
        thickness = 1.dp,
        modifier = modifier
    )
}