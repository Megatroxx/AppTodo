package com.example.apptodo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.apptodo.data.TodoItem
import com.example.apptodo.data.TodoItemsRepository

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