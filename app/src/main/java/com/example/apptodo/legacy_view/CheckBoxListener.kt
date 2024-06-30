package com.example.apptodo.legacy_view

import com.example.apptodo.data.entity.TodoItem

interface CheckBoxListener {
    fun onCheckBoxClicked(item: TodoItem, isChecked: Boolean)
}