package com.example.apptodo.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.apptodo.presentation.ThemeOption
import com.example.apptodo.presentation.common.ThemePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themePreferences: ThemePreferences
) : ViewModel() {
    private val _selectedTheme = MutableStateFlow(themePreferences.getTheme())
    val selectedTheme: StateFlow<ThemeOption> get() = _selectedTheme

    fun setTheme(theme: ThemeOption) {
        themePreferences.setTheme(theme)
        _selectedTheme.value = theme
    }
}