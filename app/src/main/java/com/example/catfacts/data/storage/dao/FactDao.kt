package com.example.catfacts.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.catfacts.data.storage.entities.FactEntity
import com.example.catfacts.data.storage.entities.FactEntity.Companion.FACT_TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCatFacts(catFacts: List<FactEntity>)

    @Query("DELETE FROM $FACT_TABLE_NAME")
    suspend fun clearAllCatFacts()

    @Query("SELECT * FROM $FACT_TABLE_NAME")
    fun getAllCatFacts(): Flow<List<FactEntity>>

    @Transaction
    suspend fun replaceAllCatFacts(catFacts: List<FactEntity>) {
        clearAllCatFacts()
        insertAllCatFacts(catFacts)
    }
}