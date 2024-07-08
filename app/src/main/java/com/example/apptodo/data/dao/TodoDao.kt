package com.example.apptodo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.apptodo.data.entity.TodoItem


/**
 * Data Access Object (DAO) for managing TodoItem entities in the Room database.
 * Defines methods for performing CRUD operations and querying TodoItem data.
 */


@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(todoItem: TodoItem)

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getItem(id: String): TodoItem?

    @Query("SELECT * FROM todo")
    suspend fun getItems(): MutableList<TodoItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveList(items: List<TodoItem>)

    @Update
    suspend fun updateItem(updatedItem: TodoItem)

    @Delete
    suspend fun deleteItem(todoItem: TodoItem)

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun deleteItemById(id: String)

    @Query("SELECT COUNT(*) FROM todo WHERE flagAchievement = 1")
    suspend fun countChecked(): Int

    @Query("UPDATE todo SET flagAchievement = :checked WHERE id = :id")
    suspend fun checkItem(id: String, checked: Boolean)
}