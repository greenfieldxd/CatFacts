package com.example.catfacts.data.network.entities

import kotlinx.serialization.Serializable

@Serializable
data class CatFactApi(
    val text: String
)