package com.example.catfacts.data.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatFact(
    @SerialName("fact")
    val text: String,
    @SerialName("length")
    val length: Int
)

@Serializable
data class CatFactResponse(
    @SerialName("current_page")
    val currentPage: Int,
    @SerialName("data")
    val facts: List<CatFact>
)