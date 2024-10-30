package com.example.catfacts.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.data.network.CatFactApi
import com.example.catfacts.data.network.RetrofitClient
import com.example.catfacts.data.storage.AppDatabase
import com.example.catfacts.data.storage.CatFactEntity
import com.example.catfacts.data.storage.CatFactRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val retrofitClient: RetrofitClient
    private val repository: CatFactRepository
    private val factsCount = 10

    private val _facts: MutableStateFlow<List<CatFactEntity>> = MutableStateFlow(emptyList())
    val facts: StateFlow<List<CatFactEntity>> = _facts.asStateFlow()

    var isLoading = mutableStateOf(true)
        private set
    var isRefreshing = mutableStateOf(false)
        private set
    var isLoadError = mutableStateOf(false)
        private set

    init {
        val catFactDao = AppDatabase.getDatabase(application).catFactDao()
        repository = CatFactRepository(catFactDao)
        retrofitClient = RetrofitClient()

        viewModelScope.launch {
            loadFactsFromDatabaseOrApi()
        }
    }

    private suspend fun loadFactsFromDatabaseOrApi() {
        repository.getAllFacts().collect { factsFromDb ->
            if (factsFromDb.isEmpty()) {
                refreshFacts(false)
            } else {
                _facts.value = factsFromDb
                isLoading.value = false
            }
        }
    }

    fun refreshFacts(showRefresh: Boolean = true) {
        viewModelScope.launch {
            supervisorScope {
                if (showRefresh) isRefreshing.value = true
                try {
                    val factsDeferred = async { getFacts(factsCount) }
                    val imagesDeferred = async { getCatImages(factsCount) }
                    val newFacts = factsDeferred.await()
                    val newImages = imagesDeferred.await()
                    val combinedFacts = newFacts.mapIndexed { index, fact ->
                        CatFactEntity(id = index, text = fact.text, imageUrl = newImages.getOrNull(index) ?: "")
                    }
                    repository.clearAllFacts()
                    combinedFacts.forEach { repository.insertCatFact(it) }
                    _facts.value = combinedFacts
                    isLoading.value = false
                    isRefreshing.value = false
                }
                catch (e: Exception){
                    isLoadError.value = true
                    isLoading.value = false
                    isRefreshing.value = false
                }
            }
        }
    }

    private suspend fun getFacts(count: Int): List<CatFactApi> {
        val service = retrofitClient.catFactService
        return service.getRandomFacts(count)
    }

    private suspend fun getCatImages(count: Int): List<String> {
        val service = retrofitClient.catImageService
        val images = service.getCatImages(count)
        return images.map { it.url }
    }
}
