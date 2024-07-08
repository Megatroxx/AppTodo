package com.example.apptodo.data.network.model


/**
 * Interface representing a generic to-do response from the server.
 */

interface GenericToDoResponse {
    val status: String
    val revision: Int
}