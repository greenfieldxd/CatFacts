package com.example.catfacts.domain.usecases

import com.example.catfacts.data.repository.ImageRepository
import com.example.catfacts.domain.entities.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface LoadCatImagesUseCase {
    suspend fun invoke() : Flow<List<Image>>
}

class LoadCatImagesUseCaseImpl @Inject constructor(
    private val imageRepository: ImageRepository
) : LoadCatImagesUseCase {
    override suspend operator fun invoke(): Flow<List<Image>> {
        return imageRepository.getCatImages().flatMapLatest { images ->
            if (images.isEmpty()) {
                imageRepository.loadNewCatImages()
            } else {
                flowOf(images)
            }
        }
    }
}
