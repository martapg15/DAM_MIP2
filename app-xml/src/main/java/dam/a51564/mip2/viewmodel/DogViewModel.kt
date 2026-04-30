package dam.a51564.mip2.viewmodel

import androidx.lifecycle.viewModelScope
import dam.a51564.mip2.core.repository.DogRepository
import dam.a51564.mip2.core.state.Resource
import dam.a51564.mip2.core.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel handling state for multiple dog images and breed filtering via the shared Core module.
 */
class DogViewModel : BaseViewModel() {

    private val repository = DogRepository()

    /** Holds the list of dog image URLs for the grid */
    private val _dogImages = MutableStateFlow<Resource<List<String>>>(Resource.Loading)
    val dogImages: StateFlow<Resource<List<String>>> = _dogImages.asStateFlow()

    /** Holds the list of available breeds loaded from the API */
    private val _breeds = MutableStateFlow<Resource<List<String>>>(Resource.Loading)
    val breeds: StateFlow<Resource<List<String>>> = _breeds.asStateFlow()

    /** The currently selected breed ("Random" by default) */
    private val _selectedBreed = MutableStateFlow("Random")
    val selectedBreed: StateFlow<String> = _selectedBreed.asStateFlow()

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
        val currentBreed = _selectedBreed.value
        viewModelScope.launch {
            val call = if (currentBreed == "Random") {
                repository.getMultipleRandomDogs(20)
            } else {
                repository.getDogsByBreed(currentBreed, 20)
            }

            call.collect { resource ->
                when (resource) {
                    is Resource.Loading -> _dogImages.value = Resource.Loading
                    is Resource.Success -> _dogImages.value = Resource.Success(resource.data.message)
                    is Resource.Error -> _dogImages.value = Resource.Error(resource.message)
                }
            }
        }
    }

    /** Fetches the full list of breeds once on startup */
    private fun fetchBreeds() {
        viewModelScope.launch {
            repository.getBreedList().collect { resource ->
                when (resource) {
                    is Resource.Loading -> _breeds.value = Resource.Loading
                    is Resource.Success -> {
                        val breedList = mutableListOf("Random")
                        breedList.addAll(resource.data.message.keys.sorted())
                        _breeds.value = Resource.Success(breedList)
                    }
                    is Resource.Error -> _breeds.value = Resource.Error(resource.message)
                }
            }
        }
    }
}
