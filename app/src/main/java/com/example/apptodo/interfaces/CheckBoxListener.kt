package com.example.apptodo.interfaces

import com.example.apptodo.data.TodoItem

interface CheckBoxListener {
    fun onCheckBoxClicked(item: TodoItem, isChecked: Boolean)
}