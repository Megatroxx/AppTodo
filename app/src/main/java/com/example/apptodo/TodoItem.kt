package com.example.apptodo

data class TodoItem(
    val id: String,
    val text: String,
    val relevance: Relevance,
    val deadline: String? = null,
    var flagAchievement: Boolean,
    val creationDate: String,
    val changeDate: String? = null,

    )
