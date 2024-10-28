package com.example.catfacts.data.storage

class CatFactRepository(private val catFactDao: CatFactDao) {
    suspend fun insertCatFact(catFact: CatFactEntity){
        catFactDao.insertCatFact(catFact)
    }

    suspend fun clearAllFacts(){
        catFactDao.clearAllFacts()
    }

    suspend fun getAllFacts(): List<CatFactEntity>{
        return catFactDao.getAllCatFacts()
    }
}