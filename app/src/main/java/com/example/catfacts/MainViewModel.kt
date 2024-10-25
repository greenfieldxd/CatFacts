import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catfacts.CatCard
import com.example.catfacts.CatFact
import com.example.catfacts.CatFactsService
import com.example.catfacts.CatImageService
import com.example.catfacts.RetrofitCatFactClient
import com.example.catfacts.RetrofitCatImageClient
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val factsCount:Int = 10
    private val _facts = mutableStateListOf<CatCard>()
    val facts: List<CatCard> get() = _facts

    init {
        viewModelScope.launch {
            val factsDeferred = async { getFacts(factsCount) }
            val imagesDeferred = async { getCatImages(factsCount) }

            val newFacts = factsDeferred.await()
            val newImages = imagesDeferred.await()

            val combinedFacts = newFacts.mapIndexed { index, fact ->
                CatCard(id = index, text = fact.text, imageUrl = newImages.getOrNull(index).toString(), mutableStateOf(false))
            }

            _facts.clear()
            _facts.addAll(combinedFacts)
        }
    }

    fun expandFact(id: Int){
        _facts.find { fact -> fact.id == id }?.let { fact -> fact.expanded.value = !fact.expanded.value }
    }

    private suspend fun getFacts(count: Int): List<CatFact> {
        val service = RetrofitCatFactClient.retrofit.create(CatFactsService::class.java)
        return service.getRandomFacts(count)
    }

    private suspend fun getCatImages(count: Int): List<String> {
        val service = RetrofitCatImageClient.retrofit.create(CatImageService::class.java)
        val images = service.getCatImages(count)
        return images.map { it.url }
    }
}
