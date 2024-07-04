package com.example.apptodo.data.network.model

data class GetSingleToDoResponse(
    override val status: String,
    val element: CloudToDoItem,
    override val revision: Int
): GenericToDoResponse
