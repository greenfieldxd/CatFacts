package com.example.catfacts.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.domain.usecases.GetCardsUseCase
import com.example.catfacts.domain.usecases.RefreshCardsUseCase
import com.example.catfacts.presentation.model.CatCard
import com.example.catfacts.presentation.state.ProgressState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase,
    private val refreshCardsUseCase: RefreshCardsUseCase
) : ViewModel() {

    private val _progressState: MutableStateFlow<ProgressState> = MutableStateFlow(ProgressState.Loading)
    val progressState: StateFlow<ProgressState> = _progressState.asStateFlow()

    private val _facts: MutableStateFlow<List<CatCard>> = MutableStateFlow(emptyList())
    val facts: StateFlow<List<CatCard>> = _facts.asStateFlow()

    init {
        loadCards()
    }

    private fun loadCards() {
        viewModelScope.launch {
            _progressState.value = ProgressState.Loading
            try {
                _facts.value = getCardsUseCase.invoke()
                _progressState.value = ProgressState.Success
            }
            catch (e: Exception){
                _progressState.value = ProgressState.Error
            }
        }
    }

    fun refreshFacts() {
        viewModelScope.launch {
            supervisorScope {
                _progressState.value = ProgressState.Refreshing
                try {
                    _facts.value = refreshCardsUseCase.invoke()
                    _progressState.value = ProgressState.Success
                }
                catch (e: Exception){
                    _progressState.value = ProgressState.Error
                }
            }
        }
    }
}
