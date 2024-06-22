package com.example.apptodo

import android.app.Application
import android.content.Context

class App : Application() {

    lateinit var todoItemsRepository: TodoItemsRepository

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences("items", Context.MODE_PRIVATE)
        todoItemsRepository = TodoItemsRepository(sharedPreferences)
    }

}