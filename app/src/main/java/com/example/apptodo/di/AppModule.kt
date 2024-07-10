package com.example.apptodo.di

import android.content.ContentResolver
import android.content.Context
import com.example.apptodo.data.database.AppDatabase
import androidx.room.Room
import com.example.apptodo.data.dao.TodoDao
import com.example.apptodo.data.network.RetrofitServ
import com.example.apptodo.data.network.TodoBackend
import com.example.apptodo.data.network.interceptors.AuthInterceptor
import com.example.apptodo.data.network.interceptors.LastKnownRevisionInterceptor
import com.example.apptodo.data.network.mapper.CloudTodoItemToEntityMapper
import com.example.apptodo.data.network.utils.NetworkChecker
import com.example.apptodo.data.repository.LastKnownRevisionRepository
import com.example.apptodo.data.repository.TodoItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }


    //возможно, ненужное
    @Provides
    @Singleton
    fun provideLastKnownRevisionRepository(): LastKnownRevisionRepository {
        return LastKnownRevisionRepository()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "todo_database"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(appDatabase: AppDatabase): TodoDao {
        return appDatabase.todoDao()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(
        authInterceptor: AuthInterceptor,
        lastKnownRevisionInterceptor: LastKnownRevisionInterceptor
    ): RetrofitServ {
        return RetrofitServ(authInterceptor, lastKnownRevisionInterceptor)
    }


    @Provides
    @Singleton
    fun provideTodoBackend(retrofitService: RetrofitServ): TodoBackend {
        return retrofitService.createTodoBackendService()
    }

    @Provides
    @Singleton
    fun provideTodoItemsRepository(
        todoDao: TodoDao,
        todoBackend: TodoBackend,
        networkChecker: NetworkChecker,
        cloudTodoItemToEntityMapper: CloudTodoItemToEntityMapper,
        lastKnownRevisionRepository: LastKnownRevisionRepository
    ): TodoItemsRepository {
        return TodoItemsRepository(
            todoDao,
            todoBackend,
            networkChecker,
            cloudTodoItemToEntityMapper,
            lastKnownRevisionRepository
        )
    }






}