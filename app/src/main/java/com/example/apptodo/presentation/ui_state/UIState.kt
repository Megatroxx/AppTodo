package com.example.apptodo.presentation.ui_state


/**
 * Represents different states of UI interaction.
 */

sealed class UIState {
    data object Idle : UIState()
    data object Loading : UIState()
    data object Success : UIState()
    data class Error(val message: String) : UIState()
}