package com.example.apptodo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.apptodo.TodoItem
import com.example.apptodo.TodoItemsRepository

class RedactorViewModel(
    private val todoItemsRepository: TodoItemsRepository
) : ViewModel() {

    private val _item = MutableLiveData<TodoItem>()
    val item: LiveData<TodoItem> get() = _item


    fun addItem(todoItem: TodoItem){
        todoItemsRepository.addItem(todoItem)
    }

    fun getItemInformation(id: String) {
        _item.value = todoItemsRepository.getItem(id)
    }

    fun deleteItem(id: String) {
        todoItemsRepository.deleteItemById(id)
    }

    companion object {
        fun factory(todoItemsRepository: TodoItemsRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    RedactorViewModel(
                        todoItemsRepository
                    )
                }
            }
    }

}