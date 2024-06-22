package com.example.apptodo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory


class ItemListViewModel(
    private val todoItemsRepository: TodoItemsRepository
) : ViewModel() {

    private val _items = MutableLiveData<List<TodoItem>>()
    val items: LiveData<List<TodoItem>> get() = _items

    private val _counter = MutableLiveData<Int>()
    val counter: LiveData<Int> get() = _counter

    fun getItems(filter: Boolean = false) {
        val todoItems = todoItemsRepository.getItems()

        val filtered = if(filter)
            todoItems.filter { !it.flagAchievement }
        else todoItems

        val countAchievement = todoItemsRepository.countChecked()

        _items.value = filtered
        _counter.value = countAchievement
    }

    private fun updateCount() {
        val countAchievement = todoItemsRepository.countChecked()
        _counter.value = countAchievement
    }

    fun deleteItem(position: Int, filter: Boolean) {
        todoItemsRepository.deleteItemByPosition(position)
        getItems(filter)
    }

    fun checkItem(item: TodoItem, checked: Boolean) {
        todoItemsRepository.checkItem(item, checked)
        updateCount()
    }

    companion object {
        fun factory(todoItemsRepository: TodoItemsRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    ItemListViewModel(
                        todoItemsRepository
                    )
                }
            }
    }

}