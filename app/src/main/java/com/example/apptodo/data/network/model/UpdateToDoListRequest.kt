package com.example.apptodo.data.network.model

class UpdateToDoListRequest(
    val status: String,
    val list: List<CloudToDoItem>
)
