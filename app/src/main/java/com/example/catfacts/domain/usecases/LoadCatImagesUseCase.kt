package com.example.catfacts.domain.usecases

import com.example.catfacts.data.repository.ImageRepository
import com.example.catfacts.domain.entities.Image
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LoadCatImagesUseCase {
    fun invoke() : Flow<List<Image>>
}

class LoadCatImagesUseCaseImpl @Inject constructor(
    private val imageRepository: ImageRepository
) : LoadCatImagesUseCase {
    override operator fun invoke(): Flow<List<Image>> {
        return imageRepository.getCatImages()
    }
}
