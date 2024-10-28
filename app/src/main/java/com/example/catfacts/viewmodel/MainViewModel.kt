import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.data.model.CatCard
import com.example.catfacts.data.network.CatFactApi
import com.example.catfacts.data.network.CatFactsService
import com.example.catfacts.data.network.CatImageService
import com.example.catfacts.data.network.RetrofitCatFactClient
import com.example.catfacts.data.network.RetrofitCatImageClient
import com.example.catfacts.data.storage.AppDatabase
import com.example.catfacts.data.storage.CatFactEntity
import com.example.catfacts.data.storage.CatFactRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CatFactRepository
    private val factsCount = 10
    private val _facts = mutableStateListOf<CatCard>()
    val facts: List<CatCard> get() = _facts

    var isLoading = mutableStateOf(true)
        private set

    var isRefreshing = mutableStateOf(false)
        private set

    init {
        val catFactDao = AppDatabase.getDatabase(application).catFactDao()
        repository = CatFactRepository(catFactDao)

        viewModelScope.launch {
            loadFactsFromDatabaseOrApi()
        }
    }

    private suspend fun loadFactsFromDatabaseOrApi() {
        val factsFromDb = repository.getAllFacts()
        if (factsFromDb.isEmpty()) {
            refreshFacts()
        } else {
            _facts.addAll(factsFromDb.map { CatCard(it.id, it.text, it.imageUrl, mutableStateOf(false)) })
            isLoading.value = false
        }
    }

    fun refreshFacts() {
        viewModelScope.launch {
            isRefreshing.value = true
            val factsDeferred = async { getFacts(factsCount) }
            val imagesDeferred = async { getCatImages(factsCount) }
            val newFacts = factsDeferred.await()
            val newImages = imagesDeferred.await()
            val combinedFacts = newFacts.mapIndexed { index, fact ->
                CatCard(id = index, text = fact.text, imageUrl = newImages.getOrNull(index) ?: "", mutableStateOf(false))
            }
            repository.clearAllFacts()
            combinedFacts.forEach { repository.insertCatFact(CatFactEntity(id = it.id, text = it.text, imageUrl = it.imageUrl)) }
            _facts.clear()
            _facts.addAll(combinedFacts)
            isRefreshing.value = false
            isLoading.value = false
        }
    }

    fun expandFact(id: Int) {
        _facts.find { fact -> fact.id == id }?.let { fact -> fact.expanded.value = !fact.expanded.value }
    }

    private suspend fun getFacts(count: Int): List<CatFactApi> {
        val service = RetrofitCatFactClient.retrofit.create(CatFactsService::class.java)
        return service.getRandomFacts(count)
    }

    private suspend fun getCatImages(count: Int): List<String> {
        val service = RetrofitCatImageClient.retrofit.create(CatImageService::class.java)
        val images = service.getCatImages(count)
        return images.map { it.url }
    }
}
