package com.example.catfacts.presentation.state

sealed class ProgressState {
    object Loading : ProgressState()
    object Success : ProgressState()
    object Refreshing : ProgressState()
    object Error : ProgressState()
}