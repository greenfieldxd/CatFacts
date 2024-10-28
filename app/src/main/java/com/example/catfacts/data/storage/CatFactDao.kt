package com.example.catfacts.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CatFactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatFact(catFact: CatFactEntity)

    @Query("DELETE FROM fact_table")
    suspend fun clearAllFacts()

    @Query("SELECT * FROM fact_table")
    suspend fun getAllCatFacts(): List<CatFactEntity>
}