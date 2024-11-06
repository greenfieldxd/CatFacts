package com.example.catfacts.presentation.state

sealed class ProgressState {
    object Loading : ProgressState()
    object Success : ProgressState()
    object IsEmpty : ProgressState()
    data class Error(val message: String?) : ProgressState()
}