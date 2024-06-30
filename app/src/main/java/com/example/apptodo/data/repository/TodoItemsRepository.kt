package com.example.apptodo.data.repository

import android.content.SharedPreferences
import com.example.apptodo.data.dao.TodoDao
import com.example.apptodo.data.entity.TodoItem
import com.example.apptodo.domain.ITodoItemsRepository
import com.example.apptodo.utils.Relevance
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoItemsRepository(
    private val todoDao: TodoDao
) : ITodoItemsRepository{


    private val itemsList = mutableListOf<TodoItem>(
        TodoItem("10", "Купить что-то", Relevance.BASE, "2024-01-01", false, "01.01.2024", null),
        TodoItem("11", "Купить яблоки", Relevance.LOW, "2024-01-01", false, "01.02.2024", null),
        TodoItem(
            "12",
            "Купить бананы и еще какой-нибудь очень длинный текст" + "потому что нужно протестировать как работает длинный текст",
            Relevance.LOW,
            null,
            false,
            "01.03.2024",
            null
        ),
        TodoItem(
            "13",
            "Помыть окна и сходить в магаз",
            Relevance.BASE,
            null,
            false,
            "01.04.2024",
            null
        ),
        TodoItem("14", "Оказываются на необитаемом острове немец, поляк и русский. После нескольких дней +" +
                "прибивает к острову бутылку. Открывают они ее, а там джинн. Джинн говорит:", Relevance.LOW, null, false, "09.07.2024", null),
        TodoItem(
            "15",
            "Сходить на почту и забрать оттуда какую-то вещь",
            Relevance.LOW,
            "2024-11-22",
            false,
            "09.07.2024",
            null
        ),
        TodoItem(
            "16", "Посадить дерево", Relevance.URGENT, null, false, "09.07.2024", null
        ),
        TodoItem(
            "17",
            "tg: @megatroxxs",
            Relevance.BASE,
            null,
            false,
            "09.07.2024",
            null
        ),
        TodoItem("18", "Построить дом", Relevance.LOW, null, false, "09.07.2024", null),
        TodoItem(
            "19",
            "Фантазия заканчивается поэтому просто какой-то текст средней длины",
            Relevance.LOW,
            "2024-05-19",
            false,
            "09.07.2024",
            null
        ),
        TodoItem(
            "20", "Купить вафель килограмма 3", Relevance.URGENT, null, false, "09.07.2024", null
        ),
        TodoItem(
            "21",
            "Купить новый ком, а то этот уже не вывозит",
            Relevance.BASE,
            null,
            false,
            "09.07.2024",
            null
        ),

        )


    init {
        CoroutineScope(Dispatchers.IO).launch {
            todoDao.saveList(itemsList)
        }
    }

    override suspend fun addItem(todoItem: TodoItem) {
        todoDao.addItem(todoItem)
    }

    override suspend fun deleteItemById(id: String) {
        todoDao.deleteItemById(id)
    }

    override suspend fun getItems(): List<TodoItem> {
        return todoDao.getItems()
    }

    override suspend fun getItem(id: String): TodoItem? {
        return todoDao.getItem(id)
    }

    override suspend fun checkItem(item: TodoItem, checked: Boolean) {
        val id = item.id
        todoDao.checkItem(id, checked)
    }

    override suspend fun countChecked(): Int {
        return todoDao.countChecked()
    }

    override suspend fun updateItem(updatedItem: TodoItem) {
        todoDao.updateItem(updatedItem)
    }

}