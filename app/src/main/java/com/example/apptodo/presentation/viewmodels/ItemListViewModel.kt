package com.example.apptodo.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptodo.data.entity.TodoItem
import com.example.apptodo.data.network.utils.NetworkChecker
import com.example.apptodo.domain.ITodoItemsRepository
import com.example.apptodo.presentation.ui_state.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


/**
 * ViewModel for managing the state and operations related to the list of todo items.
 *
 * @property todoItemsRepository Repository interface for accessing and managing todo items data.
 * @property networkChecker Utility class for checking network connectivity.
 */

class ItemListViewModel(
    private val todoItemsRepository: ITodoItemsRepository,
    private val networkChecker: NetworkChecker
) : ViewModel() {


    private val _items = MutableStateFlow<List<TodoItem>>(emptyList())
    val items = _items.asStateFlow()

    private val _uiState = MutableStateFlow<UIState>(UIState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _counter = MutableStateFlow(0)
    val counter = _counter.asStateFlow()

    private val _isVisible = MutableStateFlow<Boolean>(false)
    val isVisible = _isVisible.asStateFlow()

    private val _isNetworkAvailable = MutableStateFlow<Boolean>(false)
    val isNetworkAvailable: StateFlow<Boolean> get() = _isNetworkAvailable


    init {
        getItems(false)
        observeNetworkChanges()
    }

    private fun observeNetworkChanges() {
        viewModelScope.launch {
            networkChecker.isConnected.collect { isConnected ->
                _isNetworkAvailable.value = isConnected
                if (isConnected) {
                    delay(1000)
                    synchronizeData()
                }
            }
        }
    }


    fun checkItem(item: TodoItem, checked: Boolean) {
        runSafeInBackground {
            todoItemsRepository.checkItem(item, checked)
            updateCount()
            getItems(_isVisible.value)
        }
    }


    fun changeVisible(value: Boolean) {
        _isVisible.value = !value
        getItems(_isVisible.value)
    }


    fun getItems(filter: Boolean = false) {
        _uiState.value = UIState.Loading
        runSafeInBackground {
            try {
                val todoItems = todoItemsRepository.getItems()
                val filtered = if (filter) todoItems.filter { !it.flagAchievement } else todoItems
                val countAchievement = todoItemsRepository.countChecked()

                _items.value = filtered
                _counter.value = countAchievement
                _uiState.value = UIState.Success
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Something went wrong")
            }
        }
    }

    private fun synchronizeData() {
        _uiState.value = UIState.Loading
        runSafeInBackground {
            try {
                todoItemsRepository.synchronizeData()
                _uiState.value = UIState.Success
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Something went wrong")
            }
        }
    }


    private fun updateCount() {
        runSafeInBackground {
            val countAchievement = todoItemsRepository.countChecked()
            _counter.value = countAchievement
        }
    }

    private fun runSafeInBackground(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block.invoke()
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Something went wrong")
            }
        }
    }

}