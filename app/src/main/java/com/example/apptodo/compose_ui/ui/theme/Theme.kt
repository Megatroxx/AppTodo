package com.example.apptodo.compose_ui.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = ThemeColors.Night.supportSeparator,
    secondary = ThemeColors.Night.supportOverlay,
    tertiary = ThemeColors.Night.blue,
    error = ThemeColors.Night.red,
    scrim = ThemeColors.Night.green,
    outline = ThemeColors.Night.grayLight,
    outlineVariant = ThemeColors.Night.gray,

    surface = ThemeColors.Night.backPrimary,
    background = ThemeColors.Night.backSecondary,
    surfaceTint = ThemeColors.Night.backElevated,


    onPrimary = ThemeColors.Night.labelPrimary,
    onSecondary = ThemeColors.Night.labelSecondary ,
    onTertiary = ThemeColors.Night.labelTertiary,
    onSurfaceVariant = ThemeColors.Night.labelDisable,
)

private val LightColorScheme = lightColorScheme(
    primary = ThemeColors.Day.supportSeparator,
    secondary = ThemeColors.Day.supportOverlay,
    tertiary = ThemeColors.Day.blue,
    error = ThemeColors.Day.red,
    scrim = ThemeColors.Day.green,
    outline = ThemeColors.Day.grayLight,
    outlineVariant = ThemeColors.Day.gray,

    surface = ThemeColors.Day.backPrimary,
    background = ThemeColors.Day.backSecondary,
    surfaceTint = ThemeColors.Day.backElevated,

    onPrimary = ThemeColors.Day.labelPrimary,
    onSecondary = ThemeColors.Day.labelSecondary ,
    onTertiary = ThemeColors.Day.labelTertiary,
    onSurfaceVariant = ThemeColors.Day.labelDisable,
)

@Composable
fun ToDoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}