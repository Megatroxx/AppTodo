package com.example.apptodo.presentation.navigation


/**
 * Enum class defining destinations for navigation within the application.
 *
 * @property destString The destination string associated with each enum constant.
 */


enum class DestinationEnum(var destString : String) {
    LIST_SCREEN("listScreen"),
    REDACTOR_SCREEN("redactorScreen"),
    SETTINGS_SCREEN("settingsScreen"),
    APP_INFO_SCREEN("appInfoScreen"),

}