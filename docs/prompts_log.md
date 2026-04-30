# Prompts Log

This document tracks the primary prompts used to generate and successfully iterate upon the Dog Image Browser app using an AI programming assistant.

## Prompt 1
**Goal:** Initialize the project and generate the core architecture based on provided documentation.
**Prompt used:**
*(Implicit from guidelines)* Review all files in the `/docs` directory, and follow the exact implementation plan in `08_implementation_plan.md` to setup the MVVM foundation, Retrofit, Glide, and the initial UI.
**Result:**
The AI generated the `DogResponse` model, `DogApiService`, `DogRepository`, `DogViewModel`, and set up the custom Beige Material 3 theme across `XML` resources.

## Prompt 2
**Goal:** Address initial Gradle plugin versioning issues.
**Prompt used:**
`I am now having this problem when i try to build the project in android studio, please fix it - Error resolving plugin [id: 'org.jetbrains.kotlin.android', version: '2.0.21']`
**Result:**
The AI attempted to resolve versioning conflicts caused by `libs.versions.toml` definitions interacting with root build settings.

## Prompt 3
**Goal:** Address extension collision errors in Gradle.
**Prompt used:**
`i have this error now when building the project, please fix it - An exception occurred applying plugin request [id: 'org.jetbrains.kotlin.android']. Cannot add extension with name 'kotlin'`
**Result:**
The AI modified the plugin definitions and aliases in `app/build.gradle.kts` to avoid explicitly reapplying a Kotlin plugin that was bundled by Android Gradle Plugin 9.

## Prompt 4
**Goal:** Fix final script compilation errors caused by legacy DSL blocks.
**Prompt used:**
`now i have this problem when building, please fix it: Script compilation errors: Line 36: Unresolved reference 'kotlinOptions'.`
**Result:**
The AI analyzed the Gradle scripts and safely removed the legacy `kotlinOptions` block, allowing the built-in Android Gradle Plugin 9 Kotlin integration to correctly compile our project dependencies.

## Prompt 5
**Goal:** Outline the architecture and exact implementation plan for a new Grid and Filter feature.
**Prompt used:**
`I need to add a new feature to my app following the 'Post-Plan Feature Extensions' workflow. Please generate the exact content for a new file called docs/09_feature_extensions.md. The new feature is: Grid Layout, Breed Filter, and Image Scaling. Please format the markdown to include: Feature Name and Short Description... Expected UI Changes... Implementation Tasks... Do not write any Kotlin or XML code yet. Just generate the markdown documentation.`
**Result:**
Generated `docs/09_feature_extensions.md` providing a comprehensive, 6-step implementation plan reflecting strict MVVM principles.

## Prompt 6
**Goal:** Execute the required changes to the Network and ViewModel layers sequentially.
**Prompt used:**
`Now, referring to docs/09_feature_extensions.md, let's implement Task 1 and 2. Update the DogApiService to fetch the breed list and the MainViewModel to handle the selection logic. Keep the modern beige theme. Show me the Kotlin code for these two components.`
**Result:**
Generated and applied `BreedListResponse.kt` and `DogListResponse.kt` classes, updated the `DogApiService` interface with URL paths, and comprehensively updated `DogViewModel.kt` to natively track `dogImages` and `breeds` state via LiveData.

## Prompt 7
**Goal:** Build beautiful Grid and Spinner components in standard XML.
**Prompt used:**
`Tasks 1, 2, and 3 are complete and committed. Now, let's implement Tasks 4 and 5 from docs/09_feature_extensions.md. Create the item_dog_image.xml for the grid cells with centerCrop scaling and a 1:1 aspect ratio. Update activity_main.xml to replace the single image with a RecyclerView and add the breed selection Spinner at the top. Ensure the beige theme is maintained in the new components. Please provide the XML code only.`
**Result:**
Programmed and modified `item_dog_image.xml` with perfect 1:1 constrained aspects, and successfully stripped `ShapeableImageView` from `activity_main.xml` in favor of a `Spinner` and `RecyclerView`.

## Prompt 8
**Goal:** Conclusively wire all components together dynamically using a robust component class connection implementation.
**Prompt used:**
`I have completed the logic and the layouts. Now, let's finish the project by implementing Task 6: Connect the UI. Create the Adapter: Please write a DogImageAdapter.kt using a RecyclerView.Adapter... Update MainActivity: Initialize the RecyclerView... Observe the breeds LiveData... Set up the listener for the Spinner... Connect the Refresh Button... Please ensure the code follows the MVVM pattern and strictly uses Kotlin.`
**Result:**
Constructed `DogImageAdapter.kt` integrating Glide loading operations and seamlessly wired it into `MainActivity.kt`. Enabled smooth Grid spanning with LiveData observation, successfully completing the app's Feature Extension.

## Prompt 9
**Goal:** Update all documentation to reflect the finished project state.
**Prompt used:**
`The app is now fully functional with the grid and breed filter. Please review the whole project structure one last time to ensure it matches the requirements in docs/ and agents.md. Then, help me update my README.md to match everything i currently have and create a file prompts_log.md inside docs/ that contains the main prompts used for the creation of the project. following an example like this...`
**Result:**
Reviewed the repository for strict Kotlin UI, MVVM enforcement and Documentation alignment. Updated `README.md` and synthesized this `prompts_log.md` covering the entire workflow from origin to grid extension.

## Prompt 10
**Goal:** Final Project Cleanup and Code Audit.
**Prompt used:**
`The implementation of the Breed Filter and Grid Layout extension is finished. Before I make my final project commit, please perform a comprehensive Project Clean-Up: Code Audit... Resource Audit... Architecture Check... Update Log...`
**Result:**
Safely removed the legacy `DogResponse` model entirely, stripped out unused string resources and endpoints, explicitly verified MVVM encapsulation rules, and removed legacy fallback logic from the `DogViewModel`.

## Prompt 11
**Goal:** Modularize the project and stabilize the build environment for SDK 35/36.
**Prompt used:**
`modularize this project into three modules: :core, :app-xml, and :app-compose. Ensure everything is using the same versions and that they are stable release channels compatible with SDK 35.`
**Result:**
Successfully decoupled the business logic into `:core`, maintained the legacy View-based UI in `:app-xml`, and implemented a modern Material 3/Compose UI in `:app-compose`. Resolved critical `jlink`, `compileSdk`, and `material3` version conflicts by standardizing on Java 17 toolchains and the Compose 2024.09.00 BOM.

## Prompt 12
**Goal:** Fix network connectivity and add final UI polish (Animations/Theme harmonization).
**Prompt used:**
`Ok so now both app-xml and app-compose run just fine, but when im on inside the app both are giving me network error. Fix it please. ... perform a comprehensive review... Code Clean-up... Feature Verification...`
**Result:**
Identified and corrected the `BASE_URL` in `:core` and added missing `INTERNET` permissions to `:app-compose`. Implemented rich scale-and-fade animations for grid items and harmonized the "Modern Beige" theme across both UI modules. Verified compliance with all `/docs` requirements.

