package com.example.apptodo.data.network.model


import com.google.gson.annotations.SerializedName


/**
 * Data class representing a to-do item as received from the cloud.
 *
 * @property id The unique identifier of the to-do item.
 * @property text The text content of the to-do item.
 * @property importance The importance level of the to-do item.
 * @property deadline The deadline timestamp of the to-do item, if any.
 * @property done Indicates whether the to-do item is marked as done.
 * @property color The color associated with the to-do item for UI representation.
 * @property createdAt The timestamp when the to-do item was created.
 * @property changedAt The timestamp when the to-do item was last changed.
 * @property lastUpdatedBy The identifier of the user who last updated the to-do item.
 */


data class CloudToDoItem(
    val id: String,
    val text: String,
    val importance: String,
    val deadline: Long?,
    val done: Boolean,
    val color: String?,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("changed_at")
    val changedAt: Long,
    @SerializedName("last_updated_by")
    val lastUpdatedBy: String
)