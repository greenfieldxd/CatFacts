package com.example.catfacts.data.network.services

import com.example.catfacts.data.network.entities.CatFact
import com.example.catfacts.data.network.entities.CatFactResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CatFactsService {
    @GET("facts")
    suspend fun getRandomFacts(@Query("limit") count: Int): CatFactResponse
}

