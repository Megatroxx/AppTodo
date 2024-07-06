package com.example.apptodo.data.network.model


/**
 * Data class representing a request to update a single to-do item on the server.
 *
 * @property element The [CloudToDoItem] object containing the updated information.
 */

data class UpdateSingleToDoRequest(
    val element: CloudToDoItem
)
