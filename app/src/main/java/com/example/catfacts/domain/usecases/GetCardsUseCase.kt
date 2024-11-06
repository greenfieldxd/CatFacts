package com.example.catfacts.domain.usecases

import com.example.catfacts.domain.entities.Image
import com.example.catfacts.presentation.mappers.FactAndImageToCatCardMapper
import com.example.catfacts.presentation.model.CatCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

interface GetCardsUseCase {
    fun invoke() : Flow<List<CatCard>>
}

class GetCardsUseCaseImpl @Inject constructor(
    private val loadCatFactsUseCase: LoadCatFactsUseCase,
    private val loadCatImagesUseCase: LoadCatImagesUseCase,
    private val factAndImageToCatCardMapper : FactAndImageToCatCardMapper
) : GetCardsUseCase{

    override fun invoke(): Flow<List<CatCard>> {
        return combine(
            loadCatFactsUseCase.invoke(),
            loadCatImagesUseCase.invoke()
        ){ facts, images ->
            facts.mapIndexed { index, fact ->
                factAndImageToCatCardMapper(index = index, fact = fact, images.getOrNull(index) ?: Image("")) }
        }
    }
}


