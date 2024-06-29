package com.example.apptodo.app

import android.app.Application
import android.content.Context
import com.example.apptodo.data.TodoItemsRepository
import com.example.apptodo.viewmodels.ItemListViewModelFactory
import com.example.apptodo.viewmodels.RedactorViewModelFactory

class App : Application() {

    lateinit var todoItemsRepository: TodoItemsRepository

    lateinit var itemListViewModelFactory: ItemListViewModelFactory

    lateinit var redactorViewModelFactory: RedactorViewModelFactory

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences("items", Context.MODE_PRIVATE)
        todoItemsRepository = TodoItemsRepository(sharedPreferences)
        itemListViewModelFactory = ItemListViewModelFactory(todoItemsRepository)
        redactorViewModelFactory = RedactorViewModelFactory(todoItemsRepository)

    }

}