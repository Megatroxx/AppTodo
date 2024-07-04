package com.example.apptodo.data.network.model

data class GetToDoListResponse(
    override val status: String,
    val list: List<CloudToDoItem>,
    override val revision: Int
): GenericToDoResponse
