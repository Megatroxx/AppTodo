package com.example.apptodo.app

import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import com.example.apptodo.data.repository.LastKnownRevisionRepository
import com.example.apptodo.presentation.common.ThemePreferences
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

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideThemePreferences(sharedPreferences: SharedPreferences): ThemePreferences {
        return ThemePreferences(sharedPreferences)
    }

}