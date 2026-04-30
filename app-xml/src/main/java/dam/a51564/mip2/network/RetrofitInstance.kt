package dam.a51564.mip2.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton that provides a lazily-initialised Retrofit instance
 * configured for the Dog CEO API.
 *
 * See docs/07_api_usage.md for base URL and converter details.
 */
object RetrofitInstance {

    private const val BASE_URL = "https://dog.ceo/"

    val api: DogApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApiService::class.java)
    }
}
