package com.example.apptodo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.apptodo.data.dao.TodoDao
import com.example.apptodo.data.entity.RelevanceConverter
import com.example.apptodo.data.entity.TodoItem


/**
 * Database class for managing TodoItem entities.
 *
 * This RoomDatabase implementation provides access to the DAO (Data Access Object) for TodoItem entities.
 * It includes a companion object to create or retrieve the database instance using Room's databaseBuilder.
 *
 * @property todoDao Access object for managing TodoItem entities.
 */


@Database(entities = [TodoItem::class], version = 1, exportSchema = false)
@TypeConverters(RelevanceConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "todo_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}