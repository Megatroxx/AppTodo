package com.example.apptodo.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representing a todo item.
 *
 * @property id Unique identifier of the todo item.
 * @property text Text content of the todo item.
 * @property relevance Relevance level of the todo item.
 * @property deadline Optional deadline of the todo item in string format.
 * @property flagAchievement Flag indicating if the todo item is marked as achieved.
 * @property creationDate Date when the todo item was created.
 * @property changeDate Date when the todo item was last updated, defaults to creation date.
 */


@Entity(tableName = "todo")

data class TodoItem(
    @PrimaryKey val id: String,
    val text: String,
    val relevance: Relevance,
    val deadline: String? = null,
    var flagAchievement: Boolean,
    val creationDate: String,
    val changeDate: String = creationDate,
)