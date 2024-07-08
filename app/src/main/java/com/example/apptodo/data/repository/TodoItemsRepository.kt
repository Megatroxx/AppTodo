package com.example.apptodo.data.repository

import android.util.Log
import com.example.apptodo.data.dao.TodoDao
import com.example.apptodo.data.entity.TodoItem
import com.example.apptodo.domain.ITodoItemsRepository
import com.example.apptodo.data.network.TodoBackend
import com.example.apptodo.data.network.exception.NetworkException
import com.example.apptodo.data.network.mapper.CloudTodoItemToEntityMapper
import com.example.apptodo.data.network.model.GenericToDoResponse
import com.example.apptodo.data.network.model.UpdateSingleToDoRequest
import com.example.apptodo.data.network.model.UpdateToDoListRequest
import com.example.apptodo.data.network.utils.NetworkChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


/**
 * Repository class for managing TodoItem data, facilitating interaction
 * between the local database and remote server.
 *
 * @property todoDao DAO for accessing the local TodoItem database.
 * @property todoBackend API client for interacting with the remote server.
 * @property networkChecker Class for checking network availability.
 * @property cloudTodoItemToEntityMapper Mapper for transforming data between cloud and local TodoItem formats.
 * @property lastKnownRevisionRepository Repository for managing the last known data revision.
 */


class TodoItemsRepository(
    private val todoDao: TodoDao,
    private val todoBackend: TodoBackend,
    private val networkChecker: NetworkChecker,
    private val cloudTodoItemToEntityMapper: CloudTodoItemToEntityMapper,
    private val lastKnownRevisionRepository: LastKnownRevisionRepository
) : ITodoItemsRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        coroutineScope.launch {
            try {
                initializeData()
            } catch (e: NetworkException) {
                Log.d("AAA", "aaa")
            } catch (e: Exception) {
                Log.d("AAA", e.message.toString())
            }
        }
    }


    override suspend fun addItem(todoItem: TodoItem) {
        if (networkChecker.isNetworkAvailable()) {
            handle {
                todoBackend.addToDoItem(
                    UpdateSingleToDoRequest(cloudTodoItemToEntityMapper.mapFrom(todoItem))
                )
            }
        }
        todoDao.addItem(todoItem)
    }

    override suspend fun deleteItemById(id: String) {
        if (networkChecker.isNetworkAvailable()) {
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
        if (networkChecker.isNetworkAvailable()) {
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
        if (networkChecker.isNetworkAvailable()) {
            handle {
                todoBackend.updateToDoItem(
                    updatedItem.id,
                    UpdateSingleToDoRequest(cloudTodoItemToEntityMapper.mapFrom(updatedItem))
                )
            }
        }
        todoDao.updateItem(updatedItem)
    }

    private suspend fun initializeData() {
        if (networkChecker.isNetworkAvailable()) {
            val remoteItems = getItemsByNetwork()
            todoDao.saveList(remoteItems)
        }
    }

    override suspend fun synchronizeData() {
        val localItems = todoDao.getItems()

        val remoteItems = getItemsByNetwork()

        val changesToSync = mutableListOf<TodoItem>()


        localItems.forEach { localItem ->
            val remoteItem = remoteItems.find { it.id == localItem.id }
            if (remoteItem == null) {
                changesToSync.add(localItem)
            } else {
                if (localItem.changeDate > remoteItem.changeDate) {
                    changesToSync.add(localItem)
                }
            }
        }
        remoteItems.forEach { remoteItem ->
            val localItem = localItems.find { it.id == remoteItem.id }
            if (localItem == null) {
                todoDao.addItem(remoteItem)
            } else {
                if (remoteItem.changeDate > localItem.changeDate) {
                    changesToSync.add(remoteItem)
                }
            }
        }

        if (changesToSync.isNotEmpty()) {
            todoDao.saveList(changesToSync.toList())
            val cloudItemsToUpdate = changesToSync.map { cloudTodoItemToEntityMapper.mapFrom(it) }

            val updateRequest = UpdateToDoListRequest(
                status = "ok",
                list = cloudItemsToUpdate
            )
            handle {
                todoBackend.updateToDoList(updateRequest)
            }
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