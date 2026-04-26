package dam.a51564.mip2.repository

import dam.a51564.mip2.model.BreedListResponse
import dam.a51564.mip2.model.DogListResponse
import dam.a51564.mip2.model.DogResponse
import dam.a51564.mip2.network.RetrofitInstance

/**
 * Repository that abstracts access to the Dog CEO API.
 */
class DogRepository {

    private val api = RetrofitInstance.api

    suspend fun getRandomDog(): DogResponse {
        return api.getRandomDogImage()
    }

    suspend fun getMultipleRandomDogs(count: Int): DogListResponse {
        return api.getMultipleRandomDogs(count)
    }

    suspend fun getBreedList(): BreedListResponse {
        return api.getBreedList()
    }

    suspend fun getDogsByBreed(breed: String, count: Int): DogListResponse {
        return api.getDogsByBreed(breed, count)
    }
}
