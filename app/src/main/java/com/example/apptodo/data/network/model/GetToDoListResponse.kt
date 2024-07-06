package com.example.apptodo.data.network.model


/**
 * Data class representing the response received when fetching the to-do list from the server.
 *
 * @property status The status of the response.
 * @property list The list of [CloudToDoItem] objects retrieved from the server.
 * @property revision The revision number associated with the fetched data.
 */


data class GetToDoListResponse(
    override val status: String,
    val list: List<CloudToDoItem>,
    override val revision: Int
) : GenericToDoResponse
