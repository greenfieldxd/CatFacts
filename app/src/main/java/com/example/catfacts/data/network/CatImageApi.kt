package com.example.catfacts.data.network

import kotlinx.serialization.Serializable

@Serializable
data class CatImageApi(
    val url: String
)
