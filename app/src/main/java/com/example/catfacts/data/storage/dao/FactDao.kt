package com.example.catfacts.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.catfacts.data.storage.entities.FactEntity
import com.example.catfacts.data.storage.entities.FactEntity.Companion.FACT_TABLE_NAME

@Dao
interface FactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatFact(catFact: FactEntity)

    @Query("DELETE FROM $FACT_TABLE_NAME")
    suspend fun clearAllCatFacts()

    @Query("SELECT * FROM $FACT_TABLE_NAME")
    fun getAllCatFacts(): List<FactEntity>
}