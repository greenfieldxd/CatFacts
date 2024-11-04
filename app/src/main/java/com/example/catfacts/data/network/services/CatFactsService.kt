package com.example.catfacts.data.network.services

import com.example.catfacts.data.network.entities.CatFact
import retrofit2.http.GET
import retrofit2.http.Query

interface CatFactsService {
    @GET("facts/random")
    suspend fun getRandomFacts(@Query("amount") count: Int = 10): List<CatFact>
}

