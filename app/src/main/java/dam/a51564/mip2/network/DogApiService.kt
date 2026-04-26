package dam.a51564.mip2.network

import dam.a51564.mip2.model.DogResponse
import retrofit2.http.GET

/**
 * Retrofit service interface for the Dog CEO API.
 *
 * Endpoint: GET https://dog.ceo/api/breeds/image/random
 * See docs/07_api_usage.md for full specification.
 */
interface DogApiService {

    @GET("api/breeds/image/random")
    suspend fun getRandomDogImage(): DogResponse
}
