package com.example.catfacts.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.example.catfacts.data.storage.AppDatabase
import com.example.catfacts.data.storage.AppDatabase.Companion.DATABASE_NAME
import com.example.catfacts.data.storage.dao.FactDao
import com.example.catfacts.data.storage.dao.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
         databaseBuilder(
            context,
            AppDatabase::class.java, DATABASE_NAME
         )
             .fallbackToDestructiveMigration()
             .build()

    @Provides
    fun provideCatFactDao(database: AppDatabase): FactDao = database.catFactDao()

    @Provides
    fun provideCatImageDao(database: AppDatabase): ImageDao = database.catImageDao()
}