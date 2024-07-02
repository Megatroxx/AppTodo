package com.example.apptodo.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apptodo.domain.entity.TodoItem
import com.example.apptodo.data.repository.TodoItemsRepository
import com.example.apptodo.domain.ITodoItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ItemListViewModel(
    private val todoItemsRepository: ITodoItemsRepository
) : ViewModel() {


    private val _items = MutableStateFlow<List<TodoItem>>(emptyList())
    val items = _items.asStateFlow()

    private val _errorFlow = MutableStateFlow<String?>(null)
    val errorFlow = _errorFlow.asStateFlow()

    private val _counter = MutableStateFlow(0)
    val counter = _counter.asStateFlow()

    private val _isVisible = MutableStateFlow<Boolean>(false)
    val isVisible = _isVisible.asStateFlow()



    init{
        getItems(false)
    }



    fun checkItem(item: TodoItem, checked: Boolean) {
        runSafeInBackground {
            todoItemsRepository.checkItem(item, checked)
            updateCount()
            getItems(_isVisible.value)
        }
    }



    fun changeVisible(value: Boolean){
        _isVisible.value = !value
        getItems(_isVisible.value)
    }



    fun getItems(filter: Boolean = false) {
        runSafeInBackground {
            val todoItems = todoItemsRepository.getItems()

            val filtered = if(filter)
                todoItems.filter { !it.flagAchievement }
            else todoItems

            val countAchievement = todoItemsRepository.countChecked()

            _items.value = filtered
            _counter.value = countAchievement
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
                _errorFlow.value = e.message ?: "something went wrong"
            }
        }
    }

}