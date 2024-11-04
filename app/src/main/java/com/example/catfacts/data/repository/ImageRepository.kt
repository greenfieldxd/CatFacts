package com.example.catfacts.data.repository

import com.example.catfacts.data.network.mappers.ImageApiToEntityMapper
import com.example.catfacts.data.network.services.CatImageService
import com.example.catfacts.data.storage.dao.ImageDao
import com.example.catfacts.data.storage.mappers.ImageEntityToDomainMapper
import com.example.catfacts.domain.entities.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ImageRepository {
    fun getCatImages(): Flow<List<Image>>
    suspend fun loadNewCatImages() : Flow<List<Image>>
}

class CatImageRepositoryImpl @Inject constructor(
    private val catImageDao: ImageDao,
    private val catImageService: CatImageService,
    private val catImageEntityToDomainMapper: ImageEntityToDomainMapper,
    private val catImageApiToEntityMapper: ImageApiToEntityMapper
) : ImageRepository {
    override fun getCatImages(): Flow<List<Image>> {
        return catImageDao.getAllCatImages().map { entities ->
            entities.map { catImageEntityToDomainMapper.invoke(it) }
        }
    }

    override suspend fun loadNewCatImages() : Flow<List<Image>> {
        catImageDao.clearAllCatImages()
        val images = catImageService.getCatImages()
        val entities = images.map { catImageApiToEntityMapper.invoke(it) }
        entities.forEach { catImageDao.insertCatImage(it) }

        return catImageDao.getAllCatImages().map { entities ->
            entities.map { catImageEntityToDomainMapper.invoke(it) }
        }
    }
}