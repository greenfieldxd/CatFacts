package com.example.catfacts.data.storage.mappers

import com.example.catfacts.data.storage.entities.ImageEntity
import com.example.catfacts.domain.models.entities.Image
import javax.inject.Inject

class ImageEntityToDomainMapper @Inject constructor() : (ImageEntity) -> Image {
    override fun invoke(imageEntity: ImageEntity): Image {
        return Image(
            imageUrl = imageEntity.imageUrl
        )
    }
}