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
import com.example.apptodo.domain.ITodoItemsRepository
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


    @Provides
    @Singleton
    fun provideLastKnownRevisionRepository(): LastKnownRevisionRepository {
        return LastKnownRevisionRepository()
    }

}