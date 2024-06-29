package com.example.apptodo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptodo.data.TodoItem
import com.example.apptodo.data.TodoItemsRepository
import com.example.apptodo.utils.Relevance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RedactorViewModel(
    private val todoItemsRepository: TodoItemsRepository
) : ViewModel() {

    private val _item = MutableStateFlow<TodoItem?>(null)
    val item: StateFlow<TodoItem?> = _item

    private val _errorFlow = MutableStateFlow<String?>(null)
    val errorFlow = _errorFlow.asStateFlow()

    fun setItem(item: TodoItem) {
        _item.value = item
    }

    fun clearItem() {
        _item.value = null
    }

    fun updateText(newText: String) {
        _item.value = _item.value?.copy(text = newText)
    }

    fun updateRelevance(newRelevance: Relevance) {
        _item.value = _item.value?.copy(relevance = newRelevance)
    }

    fun updateDeadline(newDeadline: String?) {
        _item.value = _item.value?.copy(deadline = newDeadline)
    }

    fun deleteItem() {
        runSafeInBackground {
            _item.value?.let {
                todoItemsRepository.deleteItemById(it.id)
                clearItem()
            }
        }
    }


    fun saveItem(todoItem: TodoItem){
        runSafeInBackground {
            if (todoItemsRepository.getItem(todoItem.id) != null) {
                todoItemsRepository.updateItem(todoItem)
            } else {
                todoItemsRepository.addItem(todoItem)
            }
            clearItem()
        }
    }

    private fun runSafeInBackground(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block.invoke()
            } catch (e: Exception) {
                _errorFlow.value = e.message ?: "something went wrong"
            }
        }
    }
}
