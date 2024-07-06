package com.example.apptodo.presentation.ui_state

sealed class UIState {
    object Idle : UIState()
    object Loading : UIState()
    object Success : UIState()
    data class Error(val message: String) : UIState()
}