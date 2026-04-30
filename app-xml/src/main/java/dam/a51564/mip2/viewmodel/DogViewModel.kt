package dam.a51564.mip2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.a51564.mip2.repository.DogRepository
import kotlinx.coroutines.launch

/**
 * ViewModel handling state for multiple dog images and breed filtering.
 */
class DogViewModel : ViewModel() {

    private val repository = DogRepository()

    /** Holds the list of dog image URLs for the grid */
    private val _dogImages = MutableLiveData<List<String>>()
    val dogImages: LiveData<List<String>> = _dogImages

    /** Holds the list of available breeds loaded from the API */
    private val _breeds = MutableLiveData<List<String>>()
    val breeds: LiveData<List<String>> = _breeds

    /** The currently selected breed ("Random" by default) */
    private val _selectedBreed = MutableLiveData<String>("Random")
    val selectedBreed: LiveData<String> = _selectedBreed

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchBreeds()
        fetchImages()
    }

    /** Called when the user selects a new breed from the Dropdown/Spinner */
    fun setBreed(breed: String) {
        if (_selectedBreed.value != breed) {
            _selectedBreed.value = breed
            fetchImages()
        }
    }

    /** Fetches the required images based on the currently selected breed */
    fun fetchImages() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val currentBreed = _selectedBreed.value ?: "Random"
                val response = if (currentBreed == "Random") {
                    repository.getMultipleRandomDogs(20) // Let's fetch 20 for a nice grid
                } else {
                    repository.getDogsByBreed(currentBreed, 20)
                }

                if (response.status == "success") {
                    _dogImages.value = response.message
                } else {
                    _errorMessage.value = "API returned status: ${response.status}"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error occurred while fetching images"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /** Fetches the full list of breeds once on startup */
    private fun fetchBreeds() {
        viewModelScope.launch {
            try {
                val response = repository.getBreedList()
                if (response.status == "success") {
                    val breedList = mutableListOf("Random")
                    breedList.addAll(response.message.keys.sorted())
                    _breeds.value = breedList
                }
            } catch (e: Exception) {
                // If breeds fail to load, we still have "Random" by default
            }
        }
    }
}
