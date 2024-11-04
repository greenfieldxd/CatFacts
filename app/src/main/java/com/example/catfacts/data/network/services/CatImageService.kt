package com.example.catfacts.data.network.services

import com.example.catfacts.data.network.entities.CatImage
import retrofit2.http.GET
import retrofit2.http.Query

interface CatImageService {
    @GET("v1/images/search")
    suspend fun getCatImages(@Query("limit") count: Int = 10, @Query("size") size: String = "thumb"): List<CatImage>
}