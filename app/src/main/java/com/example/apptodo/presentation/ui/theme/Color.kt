package com.example.apptodo.presentation.ui.theme

import androidx.compose.ui.graphics.Color

sealed class ThemeColors(
    val supportSeparator: Color,
    val supportOverlay: Color,
    val labelPrimary: Color,
    val labelSecondary: Color,
    val labelTertiary: Color,
    val labelDisable: Color,
    val red: Color,
    val green: Color,
    val blue: Color,
    val gray: Color,
    val grayLight: Color,
    val white: Color =  Color(0xFFFFFFFF),
    val backPrimary: Color,
    val backSecondary: Color,
    val backElevated: Color,
){
    object Day : ThemeColors(
        supportSeparator = Color(0x33000000),
        supportOverlay = Color(0x0F000000),
        labelPrimary = Color(0xFF000000),
        labelSecondary = Color(0x99000000),
        labelTertiary = Color(0x4D000000),
        labelDisable = Color(0x26000000),
        red = Color(0xFFFF3B30),
        green = Color(0xFF34C759),
        blue = Color(0xFF007AFF),
        gray = Color(0xFF8E8E93),
        grayLight = Color(0xFFD1D1D6),
        backPrimary = Color(0xFFF7F6F2),
        backSecondary = Color(0xFFFFFFFF),
        backElevated = Color(0xFFFFFFFF),
    )

    object Night : ThemeColors(
        supportSeparator = Color(0x33FFFFFF),
        supportOverlay = Color(0x52000000),
        labelPrimary = Color(0xFFFFFFFF),
        labelSecondary = Color(0x99FFFFFF),
        labelTertiary = Color(0x66FFFFFF),
        labelDisable = Color(0x26FFFFFF),
        red = Color(0xFFFF453A),
        green = Color(0xFF32D74B),
        blue = Color(0xFF0A84FF),
        gray = Color(0xFF8E8E93),
        grayLight = Color(0xFF48484A),
        backPrimary = Color(0xFF161618),
        backSecondary = Color(0xFF252528),
        backElevated = Color(0xFF3C3C3F),
    )

}