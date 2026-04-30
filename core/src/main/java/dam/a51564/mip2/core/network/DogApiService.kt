package dam.a51564.mip2.core.network

import dam.a51564.mip2.core.model.BreedListResponse
import dam.a51564.mip2.core.model.DogListResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit service interface for the Dog CEO API.
 */
interface DogApiService {

    @GET("api/breeds/image/random/{count}")
    suspend fun getMultipleRandomDogs(@Path("count") count: Int): DogListResponse
    
    @GET("api/breeds/list/all")
    suspend fun getBreedList(): BreedListResponse

    @GET("api/breed/{breed}/images/random/{count}")
    suspend fun getDogsByBreed(
        @Path("breed") breed: String,
        @Path("count") count: Int
    ): DogListResponse
}
