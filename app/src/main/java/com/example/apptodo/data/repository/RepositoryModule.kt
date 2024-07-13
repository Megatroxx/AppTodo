package com.example.apptodo.data.repository

import com.example.apptodo.data.dao.TodoDao
import com.example.apptodo.data.network.TodoBackend
import com.example.apptodo.data.network.mapper.CloudTodoItemToEntityMapper
import com.example.apptodo.data.network.utils.NetworkChecker
import com.example.apptodo.domain.ITodoItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTodoItemsRepository(
        todoDao: TodoDao,
        todoBackend: TodoBackend,
        networkChecker: NetworkChecker,
        cloudTodoItemToEntityMapper: CloudTodoItemToEntityMapper,
        lastKnownRevisionRepository: LastKnownRevisionRepository
    ): ITodoItemsRepository {
        return TodoItemsRepository(
            todoDao,
            todoBackend,
            networkChecker,
            cloudTodoItemToEntityMapper,
            lastKnownRevisionRepository
        )
    }
}