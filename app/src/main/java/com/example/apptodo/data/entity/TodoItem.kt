package com.example.apptodo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.apptodo.utils.Relevance

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