package com.example.apptodo.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "todo",
)

data class TodoItem(
    @PrimaryKey val id: String,
    val text: String,
    val relevance: Relevance,
    val deadline: String? = null,
    var flagAchievement: Boolean,
    val creationDate: String,
    val changeDate: String? = null,
)