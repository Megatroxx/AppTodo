package com.example.apptodo.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptodo.data.entity.Relevance
import com.example.apptodo.data.entity.TodoItem
import com.example.apptodo.domain.ITodoItemsRepository
import com.example.apptodo.presentation.ui_state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * ViewModel for handling interactions and data logic related to editing or creating todo items.
 *
 * @property todoItemsRepository Repository interface for accessing and managing todo items data.
 */

@HiltViewModel
class RedactorViewModel @Inject constructor(
    private val todoItemsRepository: ITodoItemsRepository
) : ViewModel() {

    private val _item = MutableStateFlow<TodoItem?>(null)
    val item: StateFlow<TodoItem?> = _item

    private val _errorFlow = MutableStateFlow<String?>(null)
    val errorFlow = _errorFlow.asStateFlow()

    private val _uiState = MutableStateFlow<UIState>(UIState.Idle)
    val uiState: StateFlow<UIState> = _uiState

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
            _uiState.value = UIState.Loading
            _item.value?.let {
                todoItemsRepository.deleteItemById(it.id)
                clearItem()
                _uiState.value = UIState.Success
            }
        }
    }


    fun saveItem(todoItem: TodoItem) {
        runSafeInBackground {
            _uiState.value = UIState.Loading
            val item = todoItemsRepository.getItem(todoItem.id)
            if (item != null) {
                todoItemsRepository.updateItem(todoItem)
            } else
                todoItemsRepository.addItem(todoItem)
            clearItem()
            _uiState.value = UIState.Success
        }
    }

    private fun runSafeInBackground(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block.invoke()
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "something went wrong")
            }
        }
    }
}
