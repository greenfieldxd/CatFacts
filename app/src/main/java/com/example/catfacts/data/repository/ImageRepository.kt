package com.example.catfacts.data.repository

import com.example.catfacts.data.network.mappers.ImageApiToEntityMapper
import com.example.catfacts.data.network.services.CatImageService
import com.example.catfacts.data.storage.dao.ImageDao
import com.example.catfacts.data.storage.mappers.ImageEntityToDomainMapper
import com.example.catfacts.domain.entities.Image
import javax.inject.Inject

interface ImageRepository {
    suspend fun getCatImages(): List<Image>
    suspend fun loadNewCatImages() : List<Image>
}

class CatImageRepositoryImpl @Inject constructor(
    private val catImageDao: ImageDao,
    private val catImageService: CatImageService,
    private val catImageEntityToDomainMapper: ImageEntityToDomainMapper,
    private val catImageApiToEntityMapper: ImageApiToEntityMapper
) : ImageRepository {
    override suspend fun getCatImages(): List<Image> {
        return catImageDao.getAllCatImages().map { catImageEntityToDomainMapper.invoke(it) }
    }

    override suspend fun loadNewCatImages() : List<Image>{
        catImageDao.clearAllCatImages()
        val images = catImageService.getCatImages()
        val entities = images.map { catImageApiToEntityMapper.invoke(it) }
        entities.forEach { catImageDao.insertCatImage(it) }

        return entities.map { catImageEntityToDomainMapper.invoke(it) }
    }
}