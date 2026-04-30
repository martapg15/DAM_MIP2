# 11 – UI Contract

## Shared State Architecture

With the introduction of the `:core` module, both `:app-xml` and `:app-compose` must interact with the business logic consistently. 

### ViewModel as the Single Source of Truth
The `DogViewModel` residing in the `:core` module acts as the strict contract between the UI and the data layer. 

- **State Exposure**: The ViewModel exposes its state (e.g., `dogImages`, `breeds`, `isLoading`, `errorMessage`) using reactive streams.
  - *Current*: `LiveData` is used, which works well with XML `Observers`.
  - *Future Migration*: These will eventually be migrated to Kotlin `StateFlow` (`MutableStateFlow`, `asStateFlow()`) to natively support Jetpack Compose's state management (`collectAsState()`) while retaining XML compatibility.

### Interaction Rules
1. **No Direct Repository Access**: UI modules must never instantiate or call `DogRepository` or Retrofit directly.
2. **Action Dispatching**: User interactions (button clicks, spinner selections, pull-to-refresh) are dispatched to the ViewModel as explicit function calls (e.g., `viewModel.setBreed(breed)` or `viewModel.fetchImages()`).
3. **Passive Views**: The UI layers strictly observe the exposed state and render it visually without applying business rules or complex logic.
