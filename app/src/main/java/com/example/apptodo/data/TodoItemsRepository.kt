package com.example.apptodo.data

import android.content.SharedPreferences
import com.example.apptodo.utils.Relevance
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoItemsRepository(
    private val sharedPreferences: SharedPreferences
) {

    private val gson = Gson()

    private val ITEMS_LIST_KEY = "items_list_key"
    private val INIT_KEY = "init"

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
            if (!sharedPreferences.getBoolean(INIT_KEY, false)) {
                saveList(itemsList)
                sharedPreferences.edit().putBoolean(INIT_KEY, true).apply()
            }
        }

    }




    fun addItem(todoItem: TodoItem) {
        val items = getItems()
        val existItem = items.find { it.id == todoItem.id }
        if (existItem == null) items.add(todoItem)
        else {
            val indexOfItem = items.indexOfFirst { it.id == todoItem.id }
            items[indexOfItem] = todoItem
        }
        saveList(items)
    }



    fun saveList(data: List<TodoItem>) {
        val json = gson.toJson(data)
        sharedPreferences.edit().putString(ITEMS_LIST_KEY, json).apply()
    }


    fun deleteItemById(id: String) {
        try {
            val items = getItems()
            items.removeIf { it.id == id }
            saveList(items)
        } catch (_: Exception) {

        }
    }


    fun getItems(): MutableList<TodoItem> {
        val items = sharedPreferences.getString(ITEMS_LIST_KEY, "[]") ?: "[]"
        return Gson().fromJson(items, object : TypeToken<List<TodoItem>>() {}.type)
    }


    fun getItem(id: String): TodoItem? {
        val items = getItems()
        val item = items.find { id == it.id } ?: return null
        return item
    }


    fun checkItem(item: TodoItem, checked: Boolean) {
        val items = getItems()
        val editableItem = items.find { item.id == it.id } ?: return
        editableItem.flagAchievement = checked
        saveList(items)
    }


    fun countChecked(): Int {
        val items = getItems()
        return items.count { it.flagAchievement }
    }

    fun updateItem(updatedItem: TodoItem) {
        val items = getItems()
        val index = items.indexOfFirst { it.id == updatedItem.id }
        if (index != -1) {
            items[index] = updatedItem
            saveList(items)
        }
    }



}