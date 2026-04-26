package dam.a51564.mip2.model

import com.google.gson.annotations.SerializedName

/**
 * Data model representing the available breeds from the Dog CEO API.
 */
data class BreedListResponse(
    @SerializedName("message")
    val message: Map<String, List<String>>,
    
    @SerializedName("status")
    val status: String
)
