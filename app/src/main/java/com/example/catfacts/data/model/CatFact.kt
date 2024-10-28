package com.example.catfacts.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CatFact(
    val text: String
)