package com.example.catfacts.data.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FactEntity.Companion.FACT_TABLE_NAME)
data class FactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "text")
    val text: String
){
    companion object {
        const val FACT_TABLE_NAME = "fact_table"
    }
}