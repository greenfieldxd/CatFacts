package com.example.catfacts.data.repository

import android.util.Log
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
    suspend fun loadNewCatImages(count: Int = 5)
}

class CatImageRepositoryImpl @Inject constructor(
    private val imageDao: ImageDao,
    private val imageService: CatImageService,
    private val imageEntityToDomainMapper: ImageEntityToDomainMapper,
    private val imageApiToEntityMapper: ImageApiToEntityMapper
) : ImageRepository {
    override fun getCatImages(): Flow<List<Image>> {
        return imageDao.getAllCatImages().map { entities ->
            entities.map { imageEntityToDomainMapper.invoke(it) }
        }
    }

    override suspend fun loadNewCatImages(count: Int) {
        val images = imageService.getCatImages(count)
        if (!images.isEmpty()){
            imageDao.replaceAllCatImages(images.map { imageApiToEntityMapper.invoke(it) })
        }
    }
}