package com.example.catfacts.data.network.services

import com.example.catfacts.data.network.entities.CatFact
import retrofit2.http.GET

interface CatFactsService {
    @GET("fact")
    suspend fun getRandomFact(): CatFact
}

