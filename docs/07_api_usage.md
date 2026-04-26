# 07 – API Usage

## API: Dog CEO

- **Base URL:** `https://dog.ceo/`
- **Documentation:** [https://dog.ceo/dog-api](https://dog.ceo/dog-api)
- **Authentication:** None required.
- **Rate Limiting:** None enforced for public use.
- **Protocol:** HTTPS only.

---

## Endpoint Used

### Get Random Dog Image

| Attribute | Detail |
|-----------|--------|
| **Method** | `GET` |
| **Endpoint** | `/api/breeds/image/random` |
| **Full URL** | `https://dog.ceo/api/breeds/image/random` |
| **Auth** | None |
| **Parameters** | None |
| **Response Format** | JSON |

---

## Request

```http
GET https://dog.ceo/api/breeds/image/random
Accept: application/json
```

No request body or query parameters are required.

---

## Response

### Success (HTTP 200)

```json
{
  "message": "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg",
  "status": "success"
}
```

| Field | Type | Description |
|-------|------|-------------|
| `message` | `String` | A public HTTPS URL pointing to a random dog image (JPEG). |
| `status` | `String` | Always `"success"` for a valid response. |

### Error (HTTP 4xx / 5xx)

```json
{
  "message": "No route found for \"GET /api/breeds/image/rand\"",
  "status": "error"
}
```

| Field | Type | Description |
|-------|------|-------------|
| `message` | `String` | Human-readable error description. |
| `status` | `String` | `"error"` when the request fails. |

---

## Retrofit Integration

### Retrofit Interface – `DogApiService`

```kotlin
package com.example.mip2.network

import com.example.mip2.model.DogResponse
import retrofit2.http.GET

interface DogApiService {
    @GET("api/breeds/image/random")
    suspend fun getRandomDogImage(): DogResponse
}
```

### Retrofit Instance – `RetrofitInstance`

```kotlin
package com.example.mip2.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
```

---

## Internet Permission

The following permission must be declared in `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

---

## Error Handling Strategy

| Scenario | Handling |
|----------|----------|
| Network unavailable | `try/catch` in Repository; ViewModel posts an error state |
| API returns `"status": "error"` | ViewModel checks `status` field before posting URL |
| Image URL fails to load | Glide error placeholder shown in `ImageView` |

---

## Gradle Dependencies

Add the following to `app/build.gradle`:

```groovy
// Retrofit
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

// OkHttp (optional, for logging)
implementation 'com.squareup.okhttp3:logging-interceptor:4.11.0'

// Glide
implementation 'com.github.bumptech.glide:glide:4.16.0'
```
