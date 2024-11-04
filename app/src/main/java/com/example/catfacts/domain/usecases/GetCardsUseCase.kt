package com.example.catfacts.domain.usecases

import com.example.catfacts.domain.entities.Image
import com.example.catfacts.presentation.mappers.FactAndImageToCatCardMapper
import com.example.catfacts.presentation.model.CatCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetCardsUseCase {
    suspend fun invoke() : Flow<List<CatCard>>
}

class GetCardsUseCaseImpl @Inject constructor(
    private val loadCatFactsUseCase: LoadCatFactsUseCase,
    private val loadCatImagesUseCase: LoadCatImagesUseCase,
    private val factAndImageToCatCardMapper : FactAndImageToCatCardMapper
) : GetCardsUseCase{

    override suspend fun invoke(): Flow<List<CatCard>> = withContext(Dispatchers.IO) {
        combine(
            loadCatFactsUseCase.invoke(),
            loadCatImagesUseCase.invoke()
        ){ facts, images ->
            facts.mapIndexed { index, fact ->
                factAndImageToCatCardMapper(index = index, fact = fact, images.getOrNull(index) ?: Image("")) }
        }
    }
}


