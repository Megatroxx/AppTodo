package com.example.apptodo.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apptodo.data.network.utils.NetworkChecker
import com.example.apptodo.data.repository.TodoItemsRepository
import com.example.apptodo.domain.ITodoItemsRepository


/**
 * Factory class for creating instances of [ItemListViewModel].
 *
 * @property todoItemsRepository Repository interface for accessing and managing todo items data.
 * @property networkChecker Utility class for checking network connectivity.
 */

class ItemListViewModelFactory(
    private val todoItemsRepository: ITodoItemsRepository,
    private val networkChecker: NetworkChecker
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ItemListViewModel(
            todoItemsRepository,
            networkChecker
        ) as T
    }

}