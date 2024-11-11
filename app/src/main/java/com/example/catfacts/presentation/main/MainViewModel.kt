package com.example.catfacts.presentation.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.domain.usecases.GetCardsUseCase
import com.example.catfacts.domain.usecases.LoadNewCardsUseCase
import com.example.catfacts.presentation.model.CatCard
import com.example.catfacts.presentation.state.ProgressState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getCardsUseCase: GetCardsUseCase,
    private val loadNewCardsUseCase: LoadNewCardsUseCase
) : ViewModel() {

    private val _progressState: MutableStateFlow<ProgressState> = MutableStateFlow(ProgressState.Loading)
    val progressState: StateFlow<ProgressState> = _progressState.asStateFlow()

    val facts: StateFlow<SnapshotStateList<CatCard>> = getCardsUseCase.invoke()
        .map { it.toMutableStateList() }
        .onEach {
            if (it.isEmpty()) {
                loadNewFacts()
            } else {
                _progressState.value = ProgressState.Success
            }
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = mutableStateListOf(),
        )

    fun loadNewFacts() {
        viewModelScope.launch {
            _progressState.value = ProgressState.Loading
            try {
                loadNewCardsUseCase.invoke()
            } catch (e: Exception) {
                _progressState.value = ProgressState.Error(e.message)
            }
        }
    }
}
