# 11 – UI Contract Summary

This document defines the strict communication protocol between the UI application modules (`:app-xml`, `:app-compose`) and the shared `:core` module.

---

## The Resource Wrapper Pattern

All network-bound data is exposed via a sealed class wrapper: `Resource<T>`. This ensures that the UI layers handle all possible states of an asynchronous operation consistently.

### State Protocol
- **`Resource.Loading`**: The UI must show a progress indicator (e.g., `CircularProgressIndicator` or `ProgressBar`).
- **`Resource.Success(data)`**: The UI extracts the payload and renders the final content (e.g., Image Grid).
- **`Resource.Error(message)`**: The UI renders a meaningful error message to the user and may provide a "Retry" action.

---

## Interaction via StateFlow

The `:core` module provides repositories that return Kotlin `Flow<Resource<T>>`. The ViewModels then convert these into **`StateFlow`** to maintain a persistent UI state that survives configuration changes.

### UI Observation Contract

#### In :app-compose (Modern)
The UI uses the `collectAsState()` extension to convert the `StateFlow` directly into a reactive Compose state:
```kotlin
val dogImagesState by viewModel.dogImages.collectAsState()

Crossfade(targetState = dogImagesState) { state ->
    when (state) {
        is Resource.Loading -> ShowLoading()
        is Resource.Success -> ShowGrid(state.data)
        is Resource.Error -> ShowError(state.message)
    }
}
```

#### In :app-xml (Legacy)
The UI uses a lifecycle-aware `collect` within the `lifecycleScope` to update View-based components:
```kotlin
lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.dogImages.collect { state ->
            when (state) {
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> adapter.submitList(state.data)
                is Resource.Error -> showErrorDialog(state.message)
            }
        }
    }
}
```

---

## Action Dispatching Rules
To maintain architectural integrity, all UI actions must follow these rules:
1. **Unidirectional Control**: UI modules never instantiate the `DogRepository` or Retrofit services.
2. **Event Delegation**: All user events (Breed Selection, Refresh) are delegated to the ViewModel via simple function calls (e.g., `viewModel.fetchImages()`).
3. **Immutability**: The UI receives read-only state. Any modification to the data must go through the ViewModel's public API.
