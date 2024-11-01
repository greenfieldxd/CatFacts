package com.example.catfacts.domain.models.usecases

import com.example.catfacts.data.repository.ImageRepository
import com.example.catfacts.domain.models.entities.Fact
import com.example.catfacts.domain.models.entities.Image
import javax.inject.Inject

interface LoadCatImagesUseCase {
    suspend fun invoke() : List<Image>
}

class LoadCatImagesUseCaseImpl @Inject constructor(
    private val imageRepository: ImageRepository
) : LoadCatImagesUseCase {
    override suspend operator fun invoke(): List<Image> {
        val images = imageRepository.getCatImages()
        return if (images.isEmpty()) {
            imageRepository.loadNewCatImages()
        } else images
    }
}
