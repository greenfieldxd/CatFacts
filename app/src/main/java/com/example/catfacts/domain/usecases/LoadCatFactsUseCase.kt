package com.example.catfacts.domain.usecases

import com.example.catfacts.data.repository.FactRepository
import com.example.catfacts.domain.entities.Fact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface LoadCatFactsUseCase {
    suspend fun invoke(): Flow<List<Fact>>
}

class LoadCatFactsUseCaseImpl @Inject constructor(
    private val factRepository: FactRepository
) : LoadCatFactsUseCase {
    override suspend operator fun invoke(): Flow<List<Fact>> {
        return factRepository.getCatFacts().flatMapLatest { facts ->
            if (facts.isEmpty()) {
                factRepository.loadNewCatFacts()
            } else {
                flowOf(facts)
            }
        }
    }
}
