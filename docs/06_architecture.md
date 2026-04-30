# 06 – Architecture

## Multi-Module Architecture

The application is being refactored into a three-module architecture to separate UI from shared business logic:

1. **`:core` Module**: An Android Library module that houses the Retrofit client, data models, the `DogRepository`, and the shared `DogViewModel`. It does not contain any UI components.
2. **`:app-xml` Module**: The legacy Android application module containing the XML-based UI (`MainActivity`, `activity_main.xml`, `DogImageAdapter`). It depends on the `:core` module for its data.
3. **`:app-compose` Module**: A modern Android application module that builds its UI entirely using Jetpack Compose. It also depends on the `:core` module.

---

## Pattern: MVVM (Model-View-ViewModel)

The Dog Image Browser follows the **MVVM** architectural pattern as recommended by Google for Android development. This separates the application into distinct layers with strictly defined responsibilities.

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

## Theming Strategy

The Dog Image Browser utilizes a unified **'Modern Beige'** design language across both UI modules to ensure a consistent brand identity while leveraging platform-specific capabilities.

### Shared Design Language
- **Primary Color (Walnut)**: `#FF8B6448`
* **Background Color (Cream)**: `#FFF8F0E5`
- **Typography**: Material Design 3 type scales.

### Implementation Specifics
- **:app-xml (Static)**: Uses standard XML resources (`themes.xml`, `colors.xml`) inheriting from `Theme.Material3.DayNight.NoActionBar`.
- **:app-compose (Dynamic)**: Implements a fully dynamic **Material 3 Theming** system. It automatically reacts to the system's **Light/Dark Mode** settings using the `MaterialTheme` provider and custom `ColorScheme` definitions.

---

## Key Principles

| Principle | Application |
|-----------|-------------|
| Separation of Concerns | Each layer has one job; no cross-layer leakage |
| No Android in ViewModel | ViewModel is framework-agnostic (easier to test) |
| LiveData for reactivity | View is notified automatically on data change |
| Repository pattern | Data source can be swapped without touching ViewModel |
| Coroutines for async | Replaces callbacks; structured concurrency |
