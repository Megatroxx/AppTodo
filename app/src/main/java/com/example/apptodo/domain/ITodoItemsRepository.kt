package com.example.apptodo.domain

import com.example.apptodo.data.entity.TodoItem
import kotlinx.coroutines.flow.Flow


/**
 * Interface defining the operations available for managing TodoItem data.
 */

interface ITodoItemsRepository {

    suspend fun addItem(todoItem: TodoItem)
    suspend fun deleteItemById(id: String)
    fun getItems(): Flow<List<TodoItem>>
    suspend fun getItem(id: String): TodoItem?
    suspend fun checkItem(item: TodoItem, checked: Boolean)
    suspend fun countChecked(): Int
    suspend fun updateItem(updatedItem: TodoItem)
    suspend fun synchronizeData()

}