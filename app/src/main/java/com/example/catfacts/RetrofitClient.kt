@file:Suppress("JSON_FORMAT_REDUNDANT")

package com.example.catfacts

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitCatFactClient {
    private const val BASE_URL = "https://cat-fact.herokuapp.com/"
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            Json { ignoreUnknownKeys = true }
                .asConverterFactory("application/json".toMediaType()))
        .build()
}

object RetrofitCatImageClient {
    private const val BASE_URL = "https://api.thecatapi.com/"
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            Json { ignoreUnknownKeys = true }
                .asConverterFactory("application/json".toMediaType()))
        .build()
}

interface CatFactsService {
    @GET("facts/random")
    suspend fun getRandomFacts(@Query("amount") count: Int): List<CatFact>
}

interface CatImageService {
    @GET("v1/images/search")
    suspend fun getCatImages(@Query("limit") count: Int = 10, @Query("size") size: String = "thumb"): List<CatImage>
}




