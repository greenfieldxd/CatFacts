package com.example.catfacts.domain.models.usecases

import com.example.catfacts.data.repository.FactRepository
import com.example.catfacts.data.repository.ImageRepository
import com.example.catfacts.domain.models.entities.Image
import com.example.catfacts.presentation.mappers.FactAndImageToCatCardMapper
import com.example.catfacts.presentation.model.CatCard
import javax.inject.Inject

interface RefreshCardsUseCase {
    suspend fun invoke(): List<CatCard>
}

class RefreshCardsUseCaseImpl @Inject constructor(
    private val factRepository: FactRepository,
    private val imageRepository: ImageRepository,
    private val factAndImageToCatCardMapper : FactAndImageToCatCardMapper
) : RefreshCardsUseCase{
    override suspend fun invoke(): List<CatCard> {
        val facts = factRepository.loadNewCatFacts()
        val images = imageRepository.loadNewCatImages()

        return facts.mapIndexed { index, fact ->
            factAndImageToCatCardMapper(index = index, fact = fact, images.getOrNull(index) ?: Image(""))
        }
    }
}