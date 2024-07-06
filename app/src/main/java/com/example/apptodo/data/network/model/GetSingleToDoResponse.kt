package com.example.apptodo.data.network.model


/**
 * Data class representing the response received when fetching a single to-do item from the server.
 *
 * @property status The status of the response.
 * @property element The [CloudToDoItem] retrieved from the server.
 * @property revision The revision number associated with the fetched data.
 */

data class GetSingleToDoResponse(
    override val status: String,
    val element: CloudToDoItem,
    override val revision: Int
) : GenericToDoResponse
