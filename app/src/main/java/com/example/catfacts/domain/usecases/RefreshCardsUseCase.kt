package com.example.catfacts.domain.usecases

import com.example.catfacts.data.repository.FactRepository
import com.example.catfacts.data.repository.ImageRepository
import com.example.catfacts.domain.entities.Image
import com.example.catfacts.presentation.mappers.FactAndImageToCatCardMapper
import com.example.catfacts.presentation.model.CatCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface RefreshCardsUseCase {
    suspend fun invoke(): Flow<List<CatCard>>
}

class RefreshCardsUseCaseImpl @Inject constructor(
    private val factRepository: FactRepository,
    private val imageRepository: ImageRepository,
    private val factAndImageToCatCardMapper: FactAndImageToCatCardMapper
) : RefreshCardsUseCase {

    override suspend fun invoke(): Flow<List<CatCard>> = withContext(Dispatchers.IO) {
        combine(
            factRepository.loadNewCatFacts(),
            imageRepository.loadNewCatImages()
        ) { facts, images ->
            facts.mapIndexed { index, fact ->
                factAndImageToCatCardMapper(index = index, fact = fact, images.getOrNull(index) ?: Image(""))
            }
        }
    }
}