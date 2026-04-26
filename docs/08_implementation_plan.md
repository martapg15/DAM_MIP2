# 08 – Implementation Plan

## Overview

This document defines the **step-by-step implementation order** for the Dog Image Browser app. The AI agent **must follow this plan exactly**, generating one file or unit at a time, and verifying alignment with the architecture before moving to the next step.

> **Rule:** Do not skip steps. Do not reorder steps. Do not generate multiple files at once.

---

## Step 1 – Project Setup and Dependencies

**Goal:** Configure the Android project with all required libraries.

### Actions

1. Create a new Android project in Android Studio:
   - **Name:** `MIP2`
   - **Package:** `com.example.mip2`
   - **Language:** Kotlin
   - **Min SDK:** API 24
   - **Template:** Empty Views Activity

2. Add the following dependencies to `app/build.gradle`:

```groovy
// Retrofit + Gson
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

// OkHttp Logging (optional, useful for debugging)
implementation 'com.squareup.okhttp3:logging-interceptor:4.11.0'

// Glide
implementation 'com.github.bumptech.glide:glide:4.16.0'

// Coroutines
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'

// Lifecycle ViewModel + LiveData
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0'
implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.7.0'
```

3. Add the internet permission to `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

4. Sync the project with Gradle.

**✅ Checkpoint:** Project builds without errors. All dependencies resolve.

---

## Step 2 – Create the Data Model

**Goal:** Define the Kotlin data class that maps to the API JSON response.

### File to Create

`app/src/main/java/com/example/mip2/model/DogResponse.kt`

### Specification

- Kotlin `data class` with two fields: `message: String` and `status: String`.
- Use `@SerializedName` annotations from Gson for JSON key mapping.
- See `docs/04_data_model.md` for full specification.

**✅ Checkpoint:** `DogResponse` compiles. Fields match the API response JSON exactly.

---

## Step 3 – Define the API Interface

**Goal:** Define the Retrofit service interface and the singleton `RetrofitInstance`.

### Files to Create

1. `app/src/main/java/com/example/mip2/network/DogApiService.kt`
2. `app/src/main/java/com/example/mip2/network/RetrofitInstance.kt`

### Specification

- `DogApiService`: A Retrofit `interface` with a single `suspend` function annotated `@GET("api/breeds/image/random")` returning `DogResponse`.
- `RetrofitInstance`: A Kotlin `object` (singleton) that builds a `Retrofit` instance with base URL `https://dog.ceo/` and a Gson converter.
- See `docs/07_api_usage.md` for full code templates.

**✅ Checkpoint:** Interface and singleton compile. No runtime errors when Retrofit is initialised.

---

## Step 4 – Implement Repository and ViewModel

**Goal:** Add the business logic layer between the API and the UI.

### Files to Create

1. `app/src/main/java/com/example/mip2/repository/DogRepository.kt`
2. `app/src/main/java/com/example/mip2/viewmodel/DogViewModel.kt`

### Specification

**`DogRepository`**
- Class with a single `suspend` function `getRandomDog(): DogResponse`.
- Delegates the call to `RetrofitInstance.api`.
- Wraps the call in `try/catch` and propagates exceptions.

**`DogViewModel`**
- Extends `ViewModel()`.
- Exposes `val dogImageUrl: LiveData<String>` (backed by a `MutableLiveData`).
- Exposes `val errorMessage: LiveData<String>` for error states.
- Has a public `fun fetchRandomDogImage()` function that launches a coroutine in `viewModelScope`, calls the repository, and posts the result URL to `_dogImageUrl`.
- On error, posts an error string to `_errorMessage`.
- Calls `fetchRandomDogImage()` in `init {}` to auto-load on creation.

**✅ Checkpoint:** ViewModel compiles. LiveData emits a URL when the repository returns a successful response.

---

## Step 5 – Design the XML Layout

**Goal:** Create the XML layout file for `MainActivity`.

### File to Create

`app/src/main/res/layout/activity_main.xml`

### Specification

- Root layout: `CoordinatorLayout` or `ConstraintLayout`.
- Include an `AppBarLayout` with a `Toolbar` at the top (title: `"Dog Image Browser"`).
- `ImageView` (`id: imageViewDog`) fills the main area:
  - `scaleType="centerCrop"`
  - `contentDescription="@string/dog_image_description"`
- `Button` (`id: buttonRefresh`) pinned to the bottom-centre:
  - Text: `@string/refresh`
- All string values must be defined in `res/values/strings.xml`.
- See `docs/03_screens.md` for full layout description.

**✅ Checkpoint:** Layout renders correctly in the Android Studio XML preview without errors.

---

## Step 6 – Connect UI to ViewModel in MainActivity

**Goal:** Wire the ViewModel to the View using LiveData observers.

### File to Modify

`app/src/main/java/com/example/mip2/MainActivity.kt`

### Specification

- `MainActivity` extends `AppCompatActivity`.
- Inflate the layout: `activity_main.xml`.
- Set the `Toolbar` as the support action bar.
- Obtain the `DogViewModel` via `ViewModelProvider`.
- Observe `viewModel.dogImageUrl`:
  - On new value: use **Glide** to load the URL into `imageViewDog`.
- Observe `viewModel.errorMessage`:
  - On new value: show a `Toast` with the error message.
- Set `buttonRefresh.setOnClickListener { viewModel.fetchRandomDogImage() }`.

**✅ Checkpoint:** App runs on emulator/device. Image loads on launch. Refresh button fetches a new image.

---

## Completion Checklist

| Step | Task | Status |
|------|------|--------|
| 1 | Project setup and dependencies | ☐ |
| 2 | `DogResponse` data class | ☐ |
| 3 | `DogApiService` + `RetrofitInstance` | ☐ |
| 4 | `DogRepository` + `DogViewModel` | ☐ |
| 5 | `activity_main.xml` layout | ☐ |
| 6 | `MainActivity` wired to ViewModel | ☐ |

---

## Notes

- Mark each step as done before proceeding to the next.
- If a build error occurs at any step, **stop and fix it** before continuing.
- Do not combine steps – each step produces a verifiable, compilable unit.
