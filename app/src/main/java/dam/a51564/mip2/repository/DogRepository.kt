package dam.a51564.mip2.repository

import dam.a51564.mip2.model.DogResponse
import dam.a51564.mip2.network.RetrofitInstance

/**
 * Repository that abstracts access to the Dog CEO API.
 * The ViewModel calls this class instead of touching Retrofit directly.
 *
 * See docs/06_architecture.md – Repository Layer.
 */
class DogRepository {

    private val api = RetrofitInstance.api

    /**
     * Fetches a random dog image from the API.
     * @return [DogResponse] containing the image URL and status.
     * @throws Exception on network or server errors.
     */
    suspend fun getRandomDog(): DogResponse {
        return api.getRandomDogImage()
    }
}
