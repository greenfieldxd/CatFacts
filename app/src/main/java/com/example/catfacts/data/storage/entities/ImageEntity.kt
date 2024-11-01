package com.example.catfacts.data.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ImageEntity.Companion.IMAGE_TABLE_NAME)
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String
    ) {
    companion object {
        const val IMAGE_TABLE_NAME = "image_table"
    }
}