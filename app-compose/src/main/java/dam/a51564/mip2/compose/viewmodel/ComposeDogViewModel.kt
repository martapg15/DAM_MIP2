package dam.a51564.mip2.compose.viewmodel

import androidx.lifecycle.viewModelScope
import dam.a51564.mip2.core.repository.DogRepository
import dam.a51564.mip2.core.state.Resource
import dam.a51564.mip2.core.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Compose module consuming the shared DogRepository.
 */
class ComposeDogViewModel : BaseViewModel() {

    private val repository = DogRepository()

    private val _dogImages = MutableStateFlow<Resource<List<String>>>(Resource.Loading)
    val dogImages: StateFlow<Resource<List<String>>> = _dogImages.asStateFlow()

    private val _breeds = MutableStateFlow<Resource<List<String>>>(Resource.Loading)
    val breeds: StateFlow<Resource<List<String>>> = _breeds.asStateFlow()

    private val _selectedBreed = MutableStateFlow("Random")
    val selectedBreed: StateFlow<String> = _selectedBreed.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        fetchBreeds()
        fetchImages()
    }

    fun setBreed(breed: String) {
        if (_selectedBreed.value != breed) {
            _selectedBreed.value = breed
            fetchImages()
        }
    }

    /**
     * Fetches images for the selected breed.
     * @param isRefreshing True if triggered by pull-to-refresh.
     */
    fun fetchImages(isRefreshing: Boolean = false) {
        val currentBreed = _selectedBreed.value
        viewModelScope.launch {
            if (isRefreshing) _isRefreshing.value = true
            
            val call = if (currentBreed == "Random") {
                repository.getMultipleRandomDogs(20)
            } else {
                repository.getDogsByBreed(currentBreed, 20)
            }

            call.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        // Only set full-screen loading if not refreshing
                        if (!isRefreshing) _dogImages.value = Resource.Loading
                    }
                    is Resource.Success -> {
                        _dogImages.value = Resource.Success(resource.data.message)
                        _isRefreshing.value = false
                    }
                    is Resource.Error -> {
                        _dogImages.value = Resource.Error(resource.message)
                        _isRefreshing.value = false
                    }
                }
            }
        }
    }

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
