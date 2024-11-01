package com.example.catfacts.data.network.mappers

import com.example.catfacts.data.network.entities.CatImageApi
import com.example.catfacts.data.storage.entities.ImageEntity
import javax.inject.Inject

class ImageApiToEntityMapper @Inject constructor() : (CatImageApi) -> ImageEntity {
    override fun invoke(imageApi: CatImageApi): ImageEntity {
        return ImageEntity(
            imageUrl = imageApi.url
        )
    }
}