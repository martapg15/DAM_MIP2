# 10 – Refactoring Plan

## Extracting Business Logic and Data Access

The primary goal of this refactoring is to decouple the UI from the business logic by extracting the data layer into a shared `:core` module.

### Process:
1. **Module Creation**: We will create a new Android Library module named `:core`.
2. **Migration**: The `DogRepository`, `DogApiService`, `RetrofitInstance`, and all data models (`DogListResponse`, `BreedListResponse`) will be physically moved from the application module to `:core`.
3. **ViewModel Location**: The `DogViewModel` will also be relocated to `:core` so that both UI modules can instantiate it and observe the identical state logic.
4. **Dependency Wiring**: The `build.gradle.kts` of the UI modules (`:app-xml` and `:app-compose`) will be updated to include `implementation(project(":core"))`.
5. **Clean up**: We will resolve broken imports in the UI modules, pointing them to the new `:core` namespace.
