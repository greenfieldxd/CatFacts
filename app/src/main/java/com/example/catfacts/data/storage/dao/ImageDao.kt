package com.example.catfacts.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.catfacts.data.storage.entities.ImageEntity
import com.example.catfacts.data.storage.entities.ImageEntity.Companion.IMAGE_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatImage(catImage: ImageEntity)

    @Query("DELETE FROM $IMAGE_TABLE_NAME")
    suspend fun clearAllCatImages()

    @Query("SELECT * FROM $IMAGE_TABLE_NAME")
    fun getAllCatImages(): Flow<List<ImageEntity>>
}