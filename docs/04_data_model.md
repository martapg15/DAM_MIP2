# 04 – Data Model

## Overview

The data model represents the JSON response returned by the Dog CEO API. It is implemented as a **Kotlin data class** in the `model` package.

---

## API Response Format

When a successful request is made to:

```
GET https://dog.ceo/api/breeds/image/random
```

The API returns the following JSON structure:

```json
{
  "message": "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg",
  "status": "success"
}
```

---

## Kotlin Data Class

### `DogResponse`

**File location:** `app/src/main/java/<package>/model/DogResponse.kt`

```kotlin
package com.example.mip2.model

import com.google.gson.annotations.SerializedName

data class DogResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("status")
    val status: String
)
```

---

### Field Descriptions

| Field | JSON Key | Kotlin Type | Description |
|-------|----------|-------------|-------------|
| `message` | `"message"` | `String` | The URL of the random dog image. |
| `status` | `"status"` | `String` | API response status; expected value is `"success"`. |

---

## Design Decisions

- **`@SerializedName`** annotations are used to explicitly map JSON keys to Kotlin property names, ensuring correctness even if property names are refactored.
- The class is a **`data class`** to automatically provide `equals`, `hashCode`, `toString`, and `copy` implementations.
- No nullable types are used because both fields are always present in successful API responses. Error handling is managed at the ViewModel layer.

---

## Usage

The `DogResponse` object is produced by **Retrofit** (with a Gson converter) when the API call succeeds. The ViewModel extracts the `message` field (the image URL) and exposes it via `LiveData` to the View layer.

```
API Response (JSON)
    └── Retrofit + Gson
            └── DogResponse
                    └── DogResponse.message  ──►  LiveData<String>  ──►  ImageView (via Glide)
```
