package com.example.apptodo.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apptodo.domain.ITodoItemsRepository


/**
 * Factory for creating instances of [RedactorViewModel].
 *
 * @property todoItemsRepository Repository interface for accessing and managing todo items data.
 */

class RedactorViewModelFactory(
    private val todoItemsRepository: ITodoItemsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RedactorViewModel(
            todoItemsRepository
        ) as T
    }

}