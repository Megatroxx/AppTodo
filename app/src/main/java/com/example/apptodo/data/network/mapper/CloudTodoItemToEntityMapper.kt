package com.example.apptodo.data.network.mapper

import com.example.apptodo.data.network.model.CloudToDoItem
import com.example.apptodo.data.entity.Relevance
import com.example.apptodo.data.entity.TodoItem
import com.example.apptodo.data.repository.DeviceNameRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * Mapper class for converting between `CloudToDoItem` and `TodoItem`.
 *
 * @property deviceNameRepository Repository for retrieving the device name.
 */

class CloudTodoItemToEntityMapper(private val deviceNameRepository: DeviceNameRepository) {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    internal fun mapTo(cloudModel: CloudToDoItem): TodoItem {
        return TodoItem(
            id = cloudModel.id,
            text = cloudModel.text,
            relevance = importanceToRelevance(cloudModel.importance),
            deadline = cloudModel.deadline?.let { unixTimestampToDate(it) },
            flagAchievement = cloudModel.done,
            creationDate = unixTimestampToDate(cloudModel.createdAt),
            changeDate = unixTimestampToDate(cloudModel.changedAt)
        )
    }

    internal fun mapFrom(todoItem: TodoItem): CloudToDoItem {
        return CloudToDoItem(
            id = todoItem.id,
            text = todoItem.text,
            importance = relevanceToImportance(todoItem.relevance),
            deadline = if (todoItem.deadline != "") todoItem.deadline?.let { dateToUnixTimestamp(it) } else null,
            done = todoItem.flagAchievement,
            color = null,
            createdAt = dateToUnixTimestamp(todoItem.creationDate),
            changedAt = dateToUnixTimestamp(todoItem.changeDate),
            lastUpdatedBy = deviceNameRepository.getDeviceName()

        )
    }

    private fun importanceToRelevance(importance: String): Relevance {
        return when (importance) {
            "low" -> Relevance.LOW
            "basic" -> Relevance.BASIC
            "important" -> Relevance.IMPORTANT
            else -> Relevance.BASIC
        }
    }

    private fun relevanceToImportance(relevance: Relevance): String {
        return when (relevance) {
            Relevance.LOW -> "low"
            Relevance.BASIC -> "basic"
            Relevance.IMPORTANT -> "important"
        }
    }

    private fun dateToUnixTimestamp(dateString: String): Long {
        val date = dateFormat.parse(dateString)
        return date.time
    }

    private fun unixTimestampToDate(unixTimestamp: Long): String {
        val date = Date(unixTimestamp)
        return dateFormat.format(date)
    }
}
