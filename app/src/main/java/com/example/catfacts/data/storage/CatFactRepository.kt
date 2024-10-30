package com.example.catfacts.data.storage

import kotlinx.coroutines.flow.Flow

class CatFactRepository(private val catFactDao: CatFactDao) {
    suspend fun insertCatFact(catFact: CatFactEntity){
        catFactDao.insertCatFact(catFact)
    }

    suspend fun clearAllFacts(){
        catFactDao.clearAllFacts()
    }

    fun getAllFacts(): Flow<List<CatFactEntity>>{
        return catFactDao.getAllCatFacts()
    }
}