package com.example.catfacts.data.repository

import android.util.Log
import com.example.catfacts.data.network.mappers.FactApiToEntityMapper
import com.example.catfacts.data.network.services.CatFactsService
import com.example.catfacts.data.storage.dao.FactDao
import com.example.catfacts.data.storage.mappers.FactEntityToDomainMapper
import com.example.catfacts.domain.models.entities.Fact
import javax.inject.Inject

interface FactRepository {
    fun getCatFacts(): List<Fact>
    suspend fun loadNewCatFacts(): List<Fact>
}

class CatFactRepositoryImpl @Inject constructor(
    private val factDao: FactDao,
    private val catFactsService: CatFactsService,
    private val factApiToEntityMapper: FactApiToEntityMapper,
    private val factEntityToDomainMapper: FactEntityToDomainMapper
) : FactRepository {
    override fun getCatFacts(): List<Fact> {
        return factDao.getAllCatFacts().map { factEntityToDomainMapper.invoke(it) }
    }

    override suspend fun loadNewCatFacts(): List<Fact> {
        factDao.clearAllCatFacts()
        val facts = catFactsService.getRandomFacts()
        val entities = facts.map { factApiToEntityMapper.invoke(it) }
        entities.forEach { factDao.insertCatFact(it) }

        Log.d("LOAD_TAG", "OKAY FACTS")
        return entities.map { factEntityToDomainMapper.invoke(it) }
    }
}

