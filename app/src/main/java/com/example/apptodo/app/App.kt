package com.example.apptodo.app

import android.app.Application
import android.content.Context
import com.example.apptodo.data.database.AppDatabase
import com.example.apptodo.data.repository.TodoItemsRepository
import com.example.apptodo.viewmodels.ItemListViewModelFactory
import com.example.apptodo.viewmodels.RedactorViewModelFactory

class App : Application() {

    private lateinit var todoItemsRepository: TodoItemsRepository

    lateinit var itemListViewModelFactory: ItemListViewModelFactory

    lateinit var redactorViewModelFactory: RedactorViewModelFactory

    private lateinit var roomDataBase: AppDatabase

    override fun onCreate() {
        super.onCreate()

        roomDataBase = AppDatabase.getDatabase(this)
        todoItemsRepository = TodoItemsRepository(roomDataBase.todoDao())
        itemListViewModelFactory = ItemListViewModelFactory(todoItemsRepository)
        redactorViewModelFactory = RedactorViewModelFactory(todoItemsRepository)

    }

}