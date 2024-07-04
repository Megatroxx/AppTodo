package com.example.apptodo.view_legacy

import com.example.apptodo.data.entity.TodoItem

interface CheckBoxListener {
    fun onCheckBoxClicked(item: TodoItem, isChecked: Boolean)
}