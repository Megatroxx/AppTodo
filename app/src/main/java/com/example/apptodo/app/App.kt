package com.example.apptodo.app

import android.app.Application
import android.content.Context
import com.example.apptodo.data.database.AppDatabase
import com.example.apptodo.data.network.interceptors.AuthInterceptor
import com.example.apptodo.data.network.interceptors.LastKnownRevisionInterceptor
import com.example.apptodo.data.network.TodoBackend
import com.example.apptodo.data.network.mapper.CloudTodoItemToEntityMapper
import com.example.apptodo.data.network.utils.NetworkChecker
import com.example.apptodo.data.repository.DeviceNameRepository
import com.example.apptodo.data.repository.LastKnownRevisionRepository
import com.example.apptodo.data.repository.TodoItemsNetworkRepository
import com.example.apptodo.data.repository.TodoItemsRepository
import com.example.apptodo.domain.ITodoItemsRepository
import com.example.apptodo.presentation.viewmodels.ItemListViewModelFactory
import com.example.apptodo.presentation.viewmodels.RedactorViewModelFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class App : Application() {

    private lateinit var todoItemsRepository: ITodoItemsRepository

    lateinit var itemListViewModelFactory: ItemListViewModelFactory

    lateinit var redactorViewModelFactory: RedactorViewModelFactory

    private lateinit var roomDataBase: AppDatabase
    private lateinit var deviceNameRepository: DeviceNameRepository
    private val cloudTodoItemToEntityMapper by lazy { CloudTodoItemToEntityMapper(deviceNameRepository) }
    private val lastKnownRevisionRepository = LastKnownRevisionRepository()
    private lateinit var networkChecker: NetworkChecker

    private val retrofit = Retrofit.Builder()
        .baseUrl(TodoBackend.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .addInterceptor(LastKnownRevisionInterceptor(lastKnownRevisionRepository))
                .addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .build()

    override fun onCreate() {
        super.onCreate()

        roomDataBase = AppDatabase.getDatabase(this)
        deviceNameRepository = DeviceNameRepository(contentResolver)
        todoItemsRepository = TodoItemsRepository(
            roomDataBase.todoDao(),
            retrofit.create(TodoBackend::class.java),
            NetworkChecker(applicationContext),
            cloudTodoItemToEntityMapper,
            lastKnownRevisionRepository
        )
        itemListViewModelFactory = ItemListViewModelFactory(todoItemsRepository)
        redactorViewModelFactory = RedactorViewModelFactory(todoItemsRepository)

    }

}