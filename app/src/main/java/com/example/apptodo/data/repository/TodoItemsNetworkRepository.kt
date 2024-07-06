package com.example.apptodo.data.repository

import com.example.apptodo.data.entity.TodoItem
import com.example.apptodo.data.network.TodoBackend
import com.example.apptodo.data.network.exception.NetworkException
import com.example.apptodo.data.network.mapper.CloudTodoItemToEntityMapper
import com.example.apptodo.data.network.model.UpdateSingleToDoRequest
import com.example.apptodo.domain.ITodoItemsRepository
import com.example.apptodo.data.network.model.GenericToDoResponse
import retrofit2.Response

class TodoItemsNetworkRepository(
    private val todoBackend: TodoBackend,
    private val cloudTodoItemToEntityMapper: CloudTodoItemToEntityMapper,
    private val lastKnownRevisionRepository: LastKnownRevisionRepository
) : ITodoItemsRepository {

    override suspend fun addItem(todoItem: TodoItem) {
        handle {
            todoBackend.addToDoItem(
                UpdateSingleToDoRequest(cloudTodoItemToEntityMapper.mapFrom(todoItem))
            )
        }
    }

    override suspend fun deleteItemById(id: String) {
        handle { todoBackend.deleteItemById(id) }
    }

    override suspend fun getItems(): List<TodoItem> {
        return handle {
            todoBackend.getToDoList()
        }.list.map { cloudTodoItemToEntityMapper.mapTo(it) }
    }

    override suspend fun getItem(id: String): TodoItem {
        return handle {
            todoBackend.getToDoItemById(id)
        }.element.let { cloudTodoItemToEntityMapper.mapTo(it) }
    }

    override suspend fun checkItem(item: TodoItem, checked: Boolean) {
        handle {
            todoBackend.updateToDoItem(
                item.id,
                UpdateSingleToDoRequest(
                    cloudTodoItemToEntityMapper.mapFrom(
                        item.copy(
                            flagAchievement = checked
                        )
                    )
                )
            )
        }
    }

    override suspend fun countChecked(): Int {
        return getItems().filter { it.flagAchievement }.size
    }

    override suspend fun updateItem(updatedItem: TodoItem) {
        handle {
            todoBackend.updateToDoItem(
                updatedItem.id,
                UpdateSingleToDoRequest(cloudTodoItemToEntityMapper.mapFrom(updatedItem))
            )
        }
    }

    override suspend fun synchronizeData() {
        TODO("Not yet implemented")
    }

    private suspend fun <T : GenericToDoResponse> handle(block: suspend () -> Response<T>): T {
        val response = block.invoke()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            lastKnownRevisionRepository.updateRevision(body.revision)
            return body
        } else {
            throw NetworkException(response.errorBody()?.string())
        }
    }
}