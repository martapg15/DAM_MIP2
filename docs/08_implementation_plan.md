# 08 – Implementation Plan (Multi-Module Migration)

## Overview

This document defines the **step-by-step implementation order** for refactoring the Dog Image Browser app into a multi-module architecture. 

## Step 1 – Gradle configuration for all three modules
- Define the `:core`, `:app-xml`, and `:app-compose` modules.
- Set up `build.gradle.kts` files ensuring `:app-xml` and `:app-compose` depend on `:core` via `implementation(project(":core"))`.

## Step 2 – Migration of data/network code to :core
- Move `model`, `network`, and `repository` packages from the existing app module to the `:core` module.
- Move `DogViewModel` to `:core` or establish a shared UI state pattern to expose data to the UIs.

## Step 3 – Refactoring :app-xml to use the shared repository
- Rename the existing `app` module to `:app-xml`.
- Fix imports in `MainActivity`, `DogImageAdapter`, and resources to correctly reference the `:core` module.

## Step 4 – Implementing :app-compose with the Compose-exclusive feature
- Build the UI purely in Jetpack Compose inside the `:app-compose` module.
- Implement **Dynamic Material 3 Theming** (Light/Dark mode support).
- Introduce Compose-exclusive features like Pull-to-Refresh and rich animations.

## Step 5 – Final testing and project audit
- Verify both `app-xml` and `app-compose` compile and run successfully.
- Ensure strict separation of concerns where UI modules do not duplicate logic.
