package com.example.catfacts.data.repository

import android.util.Log
import com.example.catfacts.data.network.entities.CatFact
import com.example.catfacts.data.network.mappers.FactApiToEntityMapper
import com.example.catfacts.data.network.services.CatFactsService
import com.example.catfacts.data.storage.dao.FactDao
import com.example.catfacts.data.storage.mappers.FactEntityToDomainMapper
import com.example.catfacts.domain.entities.Fact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.mutableListOf

interface FactRepository {
    fun getCatFacts(): Flow<List<Fact>>
    suspend fun loadNewCatFacts(count: Int = 5)
}

class CatFactRepositoryImpl @Inject constructor(
    private val factDao: FactDao,
    private val factsService: CatFactsService,
    private val factApiToEntityMapper: FactApiToEntityMapper,
    private val factEntityToDomainMapper: FactEntityToDomainMapper
) : FactRepository {
    override fun getCatFacts(): Flow<List<Fact>> {
        return factDao.getAllCatFacts().map { entities ->
            entities.map { factEntityToDomainMapper.invoke(it) }
        }
    }

    override suspend fun loadNewCatFacts(count: Int) {
        //You can use a list, but then it will be the same list
        //Example in ImageRepository
        var facts = mutableListOf<CatFact>()
        repeat(count){
            val fact = factsService.getRandomFact()
            facts.add(fact)
        }

        if (!facts.isEmpty()) {
            factDao.replaceAllCatFacts(facts.map { factApiToEntityMapper.invoke(it) })
        }
    }
}

