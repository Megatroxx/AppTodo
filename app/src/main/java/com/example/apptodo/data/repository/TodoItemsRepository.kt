package com.example.apptodo.data.repository

import com.example.apptodo.data.dao.TodoDao
import com.example.apptodo.data.entity.TodoItem
import com.example.apptodo.domain.ITodoItemsRepository
import com.example.apptodo.data.entity.Relevance
import com.example.apptodo.data.network.TodoBackend
import com.example.apptodo.data.network.exception.NetworkException
import com.example.apptodo.data.network.mapper.CloudTodoItemToEntityMapper
import com.example.apptodo.data.network.model.GenericToDoResponse
import com.example.apptodo.data.network.model.UpdateSingleToDoRequest
import com.example.apptodo.data.network.utils.NetworkChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class TodoItemsRepository(
    private val todoDao: TodoDao,
    private val todoBackend: TodoBackend,
    private val networkChecker: NetworkChecker,
    private val cloudTodoItemToEntityMapper: CloudTodoItemToEntityMapper,
    private val lastKnownRevisionRepository: LastKnownRevisionRepository
) : ITodoItemsRepository{

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init{
        coroutineScope.launch {
            initializeData()
        }
    }


    override suspend fun addItem(todoItem: TodoItem) {
        if (networkChecker.isNetworkAvailable()){
            handle {
                todoBackend.addToDoItem(
                    UpdateSingleToDoRequest(cloudTodoItemToEntityMapper.mapFrom(todoItem))
                )
            }
        }
        todoDao.addItem(todoItem)
    }

    override suspend fun deleteItemById(id: String) {
        if (networkChecker.isNetworkAvailable()){
            handle { todoBackend.deleteItemById(id) }
        }
        todoDao.deleteItemById(id)
    }

    override suspend fun getItems(): List<TodoItem> {
        return todoDao.getItems()
    }

    private suspend fun getItemsByNetwork(): List<TodoItem> {
        return handle {
            todoBackend.getToDoList()
        }.list.map { cloudTodoItemToEntityMapper.mapTo(it) }
    }

    override suspend fun getItem(id: String): TodoItem? {
        return todoDao.getItem(id)
    }

    override suspend fun checkItem(item: TodoItem, checked: Boolean) {
        if (networkChecker.isNetworkAvailable()){
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
        todoDao.checkItem(item.id, checked)
    }

    override suspend fun countChecked(): Int {
        return todoDao.countChecked()
    }

    override suspend fun updateItem(updatedItem: TodoItem) {
        if (networkChecker.isNetworkAvailable()){
            handle {
                todoBackend.updateToDoItem(
                    updatedItem.id,
                    UpdateSingleToDoRequest(cloudTodoItemToEntityMapper.mapFrom(updatedItem))
                )
            }
        }
        todoDao.updateItem(updatedItem)
    }

    suspend fun initializeData() {
        if (networkChecker.isNetworkAvailable()) {
            val remoteItems = getItemsByNetwork()
            todoDao.saveList(remoteItems)
        }
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