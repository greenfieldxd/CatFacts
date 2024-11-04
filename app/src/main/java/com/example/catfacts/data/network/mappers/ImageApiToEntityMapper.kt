package com.example.catfacts.data.network.mappers

import com.example.catfacts.data.network.entities.CatImage
import com.example.catfacts.data.storage.entities.ImageEntity
import javax.inject.Inject

class ImageApiToEntityMapper @Inject constructor() : (CatImage) -> ImageEntity {
    override fun invoke(imageApi: CatImage): ImageEntity {
        return ImageEntity(
            imageUrl = imageApi.url
        )
    }
}