package com.example.catfacts.domain.usecases

import com.example.catfacts.data.repository.FactRepository
import com.example.catfacts.domain.entities.Fact
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LoadCatFactsUseCase {
    fun invoke(): Flow<List<Fact>>
}

class LoadCatFactsUseCaseImpl @Inject constructor(
    private val factRepository: FactRepository
) : LoadCatFactsUseCase {
    override operator fun invoke(): Flow<List<Fact>> {
        return factRepository.getCatFacts()
    }
}
