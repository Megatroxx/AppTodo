package com.example.apptodo

interface CheckBoxListener {
    fun onCheckBoxClicked(item: TodoItem, isChecked: Boolean)
}