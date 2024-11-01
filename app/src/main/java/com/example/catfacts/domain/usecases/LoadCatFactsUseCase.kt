package com.example.catfacts.domain.usecases

import com.example.catfacts.data.repository.FactRepository
import com.example.catfacts.domain.entities.Fact
import javax.inject.Inject

interface LoadCatFactsUseCase {
    suspend fun invoke() : List<Fact>
}

class LoadCatFactsUseCaseImpl @Inject constructor(
    private val factRepository: FactRepository
) : LoadCatFactsUseCase {
    override suspend operator fun invoke(): List<Fact> {
        val facts = factRepository.getCatFacts()
        return if (facts.isEmpty()) {
            factRepository.loadNewCatFacts()
        } else facts
    }
}
