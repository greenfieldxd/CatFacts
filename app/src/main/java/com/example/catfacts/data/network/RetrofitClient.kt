package com.example.catfacts.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

class RetrofitClient {
    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                Json { ignoreUnknownKeys = true }
                    .asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    val catFactService: CatFactsService by lazy {
        createRetrofit(CAT_FACT_BASE_URL).create(CatFactsService::class.java)
    }

    val catImageService: CatImageService by lazy {
        createRetrofit(CAT_IMAGE_BASE_URL).create(CatImageService::class.java)
    }

    companion object{
        private const val CAT_FACT_BASE_URL = "https://cat-fact.herokuapp.com/"
        private const val CAT_IMAGE_BASE_URL = "https://api.thecatapi.com/"
    }
}

interface CatFactsService {
    @GET("facts/random")
    suspend fun getRandomFacts(@Query("amount") count: Int): List<CatFactApi>
}

interface CatImageService {
    @GET("v1/images/search")
    suspend fun getCatImages(@Query("limit") count: Int = 10, @Query("size") size: String = "thumb"): List<CatImageApi>
}
