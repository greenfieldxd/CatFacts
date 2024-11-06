package com.example.catfacts.domain.usecases

import com.example.catfacts.data.repository.FactRepository
import com.example.catfacts.data.repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ClearCardsUseCase {
    suspend fun invoke()
}

class ClearCardsUseCaseImpl @Inject constructor(
    private val factRepository: FactRepository,
    private val imageRepository: ImageRepository,
) : ClearCardsUseCase {
    override suspend fun invoke() = withContext(Dispatchers.IO) {
        factRepository.clearAllCatFact()
        imageRepository.clearAllCatImages()
    }
}