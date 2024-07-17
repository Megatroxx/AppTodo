package com.example.apptodo.presentation.common

import android.content.SharedPreferences
import com.example.apptodo.presentation.ThemeOption

class ThemePreferences(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val THEME_KEY = "theme_key"
    }

    fun getTheme(): ThemeOption {
        val themeName = sharedPreferences.getString(THEME_KEY, ThemeOption.System.name) ?: ThemeOption.System.name
        return ThemeOption.valueOf(themeName)
    }

    fun setTheme(theme: ThemeOption) {
        sharedPreferences.edit().putString(THEME_KEY, theme.name).apply()
    }
}