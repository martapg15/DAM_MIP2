package dam.a51564.mip2.model

import com.google.gson.annotations.SerializedName

/**
 * Data model for endpoints that return multiple dog image URLs.
 */
data class DogListResponse(
    @SerializedName("message")
    val message: List<String>,

    @SerializedName("status")
    val status: String
)
