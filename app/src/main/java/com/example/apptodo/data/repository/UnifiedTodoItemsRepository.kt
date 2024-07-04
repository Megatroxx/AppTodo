package com.example.apptodo.data.repository

import com.example.apptodo.data.entity.TodoItem
import com.example.apptodo.data.network.utils.NetworkChecker
import com.example.apptodo.domain.ITodoItemsRepository

class UnifiedTodoItemsRepository(
    private val localRepository: TodoItemsRepository,
    private val networkRepository: TodoItemsNetworkRepository,
    private val networkChecker: NetworkChecker
) : ITodoItemsRepository {


    override suspend fun addItem(todoItem: TodoItem) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItemById(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getItems(): List<TodoItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getItem(id: String): TodoItem? {
        TODO("Not yet implemented")
    }

    override suspend fun checkItem(item: TodoItem, checked: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun countChecked(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun updateItem(updatedItem: TodoItem) {
        TODO("Not yet implemented")
    }
}