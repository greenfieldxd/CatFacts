package com.example.catfacts.data.repository

import com.example.catfacts.data.network.entities.CatFact
import com.example.catfacts.data.network.mappers.FactApiToEntityMapper
import com.example.catfacts.data.network.services.CatFactsService
import com.example.catfacts.data.storage.dao.FactDao
import com.example.catfacts.data.storage.mappers.FactEntityToDomainMapper
import com.example.catfacts.domain.entities.Fact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface FactRepository {
    fun getCatFacts(): Flow<List<Fact>>
    suspend fun loadNewCatFacts(count: Int = 5)
    suspend fun clearAllCatFact()
}

class CatFactRepositoryImpl @Inject constructor(
    private val factDao: FactDao,
    private val catFactsService: CatFactsService,
    private val factApiToEntityMapper: FactApiToEntityMapper,
    private val factEntityToDomainMapper: FactEntityToDomainMapper
) : FactRepository {
    override fun getCatFacts(): Flow<List<Fact>> {
        return factDao.getAllCatFacts().map { entities ->
            entities.map { factEntityToDomainMapper.invoke(it) }
        }
    }

    override suspend fun loadNewCatFacts(count: Int) {
        catFactsService.getRandomFacts(count).facts
            .map { factApiToEntityMapper.invoke(it) }
            .forEach { factDao.insertCatFact(it) }
    }

    override suspend fun clearAllCatFact() {
        factDao.clearAllCatFacts()
    }
}

