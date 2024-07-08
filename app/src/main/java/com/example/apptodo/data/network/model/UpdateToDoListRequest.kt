package com.example.apptodo.data.network.model


/**
 * Data class representing a request to update the to-do list on the server.
 *
 * @property status The status of the update request.
 * @property list The list of [CloudToDoItem] objects to be updated on the server.
 */

class UpdateToDoListRequest(
    val status: String,
    val list: List<CloudToDoItem>
)
