package com.example.apptodo.app

import android.app.Application
import android.content.Context
import com.example.apptodo.data.TodoItemsRepository

class App : Application() {

    lateinit var todoItemsRepository: TodoItemsRepository

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences("items", Context.MODE_PRIVATE)
        todoItemsRepository = TodoItemsRepository(sharedPreferences)
    }

}