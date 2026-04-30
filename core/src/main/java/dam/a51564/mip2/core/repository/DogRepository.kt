package dam.a51564.mip2.core.repository

import dam.a51564.mip2.core.model.BreedListResponse
import dam.a51564.mip2.core.model.DogListResponse
import dam.a51564.mip2.core.network.RetrofitInstance
import dam.a51564.mip2.core.state.Resource
import kotlinx.coroutines.flow.Flow

class DogRepository : BaseRepository() {
    
    private val api = RetrofitInstance.api

    fun getMultipleRandomDogs(count: Int): Flow<Resource<DogListResponse>> {
        return safeApiCall { api.getMultipleRandomDogs(count) }
    }

    fun getBreedList(): Flow<Resource<BreedListResponse>> {
        return safeApiCall { api.getBreedList() }
    }

    fun getDogsByBreed(breed: String, count: Int): Flow<Resource<DogListResponse>> {
        return safeApiCall { api.getDogsByBreed(breed, count) }
    }
}
