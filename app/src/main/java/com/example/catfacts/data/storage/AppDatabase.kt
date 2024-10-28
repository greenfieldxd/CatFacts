package com.example.catfacts.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CatFactEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catFactDao(): CatFactDao

    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cat_fact_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}