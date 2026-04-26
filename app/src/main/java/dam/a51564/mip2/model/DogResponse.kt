package dam.a51564.mip2.model

import com.google.gson.annotations.SerializedName

/**
 * Data model representing the JSON response from the Dog CEO API.
 *
 * API response shape:
 * {
 *   "message": "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg",
 *   "status":  "success"
 * }
 *
 * See docs/04_data_model.md for full specification.
 */
data class DogResponse(
    /** URL of the random dog image. */
    @SerializedName("message")
    val message: String,

    /** API response status; expected value is "success". */
    @SerializedName("status")
    val status: String
)
