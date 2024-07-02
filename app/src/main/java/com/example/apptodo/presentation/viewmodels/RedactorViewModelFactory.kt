package com.example.apptodo.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apptodo.data.repository.TodoItemsRepository

class RedactorViewModelFactory(
    private val todoItemsRepository : TodoItemsRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RedactorViewModel(
            todoItemsRepository
        ) as T
    }

}