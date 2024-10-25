package com.example.catfacts

import androidx.compose.runtime.MutableState

data class CatCard(
    val id: Int,
    val text: String,
    val imageUrl: String,
    var expanded: MutableState<Boolean>
)
