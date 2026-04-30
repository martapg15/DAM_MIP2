# 10 – Refactoring Plan

## Extracting Business Logic and Data Access

The primary goal of this refactoring is to decouple the UI from the business logic by extracting the data layer into a shared `:core` module.

### Process Summary:
1. **Module Creation**: We created a new Android Library module named `:core` to act as the single source of truth.
2. **Data Extraction**: The `DogRepository`, `DogApiService`, `RetrofitInstance`, and all data models were physically moved to `:core`. We ensured that `:core` remains independent of the Android UI framework.
3. **State Management Migration**: We migrated from a monolithic `DogViewModel` to a shared `BaseViewModel` and specialized implementations in the `:core` package. We transitioned from plain LiveData to Kotlin **StateFlow** to natively support the modern `:app-compose` requirement.
4. **Dependency Wiring**: The `build.gradle.kts` of the UI modules (`:app-xml` and `:app-compose`) were updated to include `implementation(project(":core"))`.
5. **Architectural Harmonization**: We resolved all package-level conflicts and ensured that both UI layers consume the same `Resource<T>` state wrapper, guaranteeing a unified user experience across platforms.
