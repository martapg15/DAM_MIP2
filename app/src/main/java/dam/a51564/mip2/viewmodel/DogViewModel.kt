package dam.a51564.mip2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.a51564.mip2.repository.DogRepository
import kotlinx.coroutines.launch

/**
 * ViewModel that exposes the dog image URL and error state as LiveData.
 * Auto-fetches on creation; the View can trigger additional fetches
 * via [fetchRandomDogImage].
 *
 * See docs/06_architecture.md – ViewModel Layer.
 */
class DogViewModel : ViewModel() {

    private val repository = DogRepository()

    /** Holds the most recent image URL. */
    private val _dogImageUrl = MutableLiveData<String>()
    val dogImageUrl: LiveData<String> = _dogImageUrl

    /** Holds a user-facing error message (null when no error). */
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    /** Loading state so the UI can show/hide a progress indicator. */
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchRandomDogImage()
    }

    /**
     * Launches a coroutine that fetches a random dog image from the
     * repository and posts the result to [_dogImageUrl] on success,
     * or an error string to [_errorMessage] on failure.
     */
    fun fetchRandomDogImage() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getRandomDog()
                if (response.status == "success") {
                    _dogImageUrl.value = response.message
                } else {
                    _errorMessage.value = "API returned status: ${response.status}"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
