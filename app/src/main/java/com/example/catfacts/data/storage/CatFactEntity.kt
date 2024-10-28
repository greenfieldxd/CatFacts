package com.example.catfacts.data.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fact_table")
data class CatFactEntity(
    @PrimaryKey val id:Int = 0,
    val text: String,
    val imageUrl: String
)