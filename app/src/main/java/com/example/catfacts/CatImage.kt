package com.example.catfacts

import kotlinx.serialization.Serializable

@Serializable
data class CatImage(
    val url: String
)
