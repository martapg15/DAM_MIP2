# 06 – Architecture

## Pattern: MVVM (Model-View-ViewModel)

The Dog Image Browser follows the **MVVM** architectural pattern as recommended by Google for Android development. This separates the application into three distinct layers with strictly defined responsibilities.

---

## Layer Overview

```
┌──────────────────────────────────────────────────┐
│                    VIEW LAYER                    │
│  MainActivity + activity_main.xml                │
│  - Observes LiveData from ViewModel              │
│  - Updates UI (ImageView, Button)                │
│  - No business logic                            │
└────────────────────┬─────────────────────────────┘
                     │ observes LiveData
                     ▼
┌──────────────────────────────────────────────────┐
│                 VIEWMODEL LAYER                  │
│  DogViewModel                                    │
│  - Exposes LiveData<String> (image URL)          │
│  - Calls Repository to fetch data               │
│  - Survives configuration changes               │
└────────────────────┬─────────────────────────────┘
                     │ calls
                     ▼
┌──────────────────────────────────────────────────┐
│                REPOSITORY LAYER                  │
│  DogRepository                                   │
│  - Abstracts the data source                    │
│  - Calls DogApiService (Retrofit)               │
└────────────────────┬─────────────────────────────┘
                     │ HTTP request
                     ▼
┌──────────────────────────────────────────────────┐
│                  MODEL LAYER                     │
│  DogResponse (data class)                        │
│  DogApiService (Retrofit interface)              │
│  - Raw data shape from the API                  │
└──────────────────────────────────────────────────┘
```

---

## Layer Responsibilities

### View – `MainActivity`

- **Responsibility:** Display data; react to user input.
- **Allowed imports:** Android UI framework (`AppCompatActivity`, `ImageView`, `Button`), `ViewModelProvider`, Glide.
- **Forbidden:** Direct API calls, business logic, network code.
- **Communication:** Observes `LiveData` published by `DogViewModel`.

```kotlin
// Example (pseudo-code)
viewModel.dogImageUrl.observe(this) { url ->
    Glide.with(this).load(url).into(imageViewDog)
}
```

---

### ViewModel – `DogViewModel`

- **Responsibility:** Hold UI state; coordinate between View and Repository.
- **Allowed imports:** `ViewModel`, `LiveData`, `MutableLiveData`, `viewModelScope`, coroutines.
- **Forbidden:** Android `Context`, direct UI references, `Activity` imports.
- **Survives:** Configuration changes (e.g., screen rotation).

```kotlin
// Example (pseudo-code)
class DogViewModel : ViewModel() {
    private val _dogImageUrl = MutableLiveData<String>()
    val dogImageUrl: LiveData<String> = _dogImageUrl

    fun fetchRandomDogImage() {
        viewModelScope.launch {
            val response = repository.getRandomDog()
            _dogImageUrl.value = response.message
        }
    }
}
```

---

### Repository – `DogRepository`

- **Responsibility:** Abstract data access; the single source of truth.
- **Allowed imports:** Retrofit service interface, `DogResponse`.
- **Forbidden:** UI references, LiveData.

```kotlin
// Example (pseudo-code)
class DogRepository {
    private val api = RetrofitInstance.api

    suspend fun getRandomDog(): DogResponse {
        return api.getRandomDogImage()
    }
}
```

---

### Model – `DogResponse` + `DogApiService`

- **Responsibility:** Define the data shape and the API contract.
- `DogResponse` is a plain Kotlin data class.
- `DogApiService` is a Retrofit interface with `suspend` functions.

---

## Package Structure

```
com.example.mip2/
├── model/
│   └── DogResponse.kt
├── network/
│   ├── DogApiService.kt
│   └── RetrofitInstance.kt
├── repository/
│   └── DogRepository.kt
├── viewmodel/
│   └── DogViewModel.kt
└── MainActivity.kt
```

---

## Key Principles

| Principle | Application |
|-----------|-------------|
| Separation of Concerns | Each layer has one job; no cross-layer leakage |
| No Android in ViewModel | ViewModel is framework-agnostic (easier to test) |
| LiveData for reactivity | View is notified automatically on data change |
| Repository pattern | Data source can be swapped without touching ViewModel |
| Coroutines for async | Replaces callbacks; structured concurrency |
