package com.example.catfacts.domain.models.usecases

import com.example.catfacts.domain.models.entities.Image
import com.example.catfacts.presentation.mappers.FactAndImageToCatCardMapper
import com.example.catfacts.presentation.model.CatCard
import javax.inject.Inject

interface GetCardsUseCase {
    suspend fun invoke() : List<CatCard>
}

class GetCardsUseCaseImpl @Inject constructor(
    private val loadCatFactsUseCase: LoadCatFactsUseCase,
    private val loadCatImagesUseCase: LoadCatImagesUseCase,
    private val factAndImageToCatCardMapper : FactAndImageToCatCardMapper
) : GetCardsUseCase{

    override suspend fun invoke(): List<CatCard> {
        val facts = loadCatFactsUseCase.invoke()
        val images = loadCatImagesUseCase.invoke()

        return facts.mapIndexed { index, fact ->
            factAndImageToCatCardMapper(index = index, fact = fact, images.getOrNull(index) ?: Image(""))
        }
    }
}

