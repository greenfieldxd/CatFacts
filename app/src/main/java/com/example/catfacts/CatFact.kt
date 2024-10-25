package com.example.catfacts

import kotlinx.serialization.Serializable

@Serializable
data class CatFact(
    val text: String
)