package com.example.catfacts.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.catfacts.data.storage.dao.FactDao
import com.example.catfacts.data.storage.dao.ImageDao
import com.example.catfacts.data.storage.entities.FactEntity
import com.example.catfacts.data.storage.entities.ImageEntity

@Database(entities = [FactEntity::class, ImageEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catFactDao(): FactDao
    abstract fun catImageDao(): ImageDao

    companion object{
        const val DATABASE_NAME = "cat_fact_database"
    }
}