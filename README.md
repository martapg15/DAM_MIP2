# MIP-3 Dog Image Browser (Multi-Module)

## Project Objective
This project demonstrates a robust **Multi-Module Android Architecture** refactored from a monolithic codebase. The goal is to separate business logic from the UI layer, enabling parallel development of legacy (XML) and modern (Compose) user interfaces.

---

## Module Architecture

The project is split into three distinct modules to ensure separation of concerns:

### 1. `:core` (The Shared Brain)
- **Responsibility**: Contains all business logic, network configuration, and data management.
- **Key Components**:
    - **Retrofit Client**: Configured for the Dog CEO API.
    - **Repositories**: `DogRepository` handles data fetching and mapping.
    - **Resource Wrapper**: A generic sealed class used to communicate network states (Loading, Success, Error) across modules.
    - **Shared Logic**: Holds the core data models used by both UI modules.

### 2. `:app-xml` (Legacy UI)
- **Responsibility**: The stable, View-based implementation of the Dog Browser.
- **Tech Stack**: XML Layouts, `RecyclerView` with Grid Layout, `Spinner` for filtering, and `ViewBinding`.
- **Dependency**: Consumes data exclusively from the `:core` module.

### 3. `:app-compose` (Modern UI)
- **Responsibility**: A cutting-edge UI implementation using Jetpack Compose and Material 3.
- **Tech Stack**: Pure Compose, `LazyVerticalGrid`, and `StateFlow` for reactive updates.
- **Dependency**: Consumes data exclusively from the `:core` module.

---

## Compose-Exclusive Features

The `:app-compose` module includes several advanced features not present in the legacy module:

- **Dynamic Material 3 Theming**: Full support for system-wide **Light and Dark Mode**. It includes Android 12+ Dynamic Color support with a custom "Modern Beige" fallback palette.
- **Refresh FAB & State Tracking**: A reactive Floating Action Button that tracks the network state and provides feedback during manual refreshes.
- **Rich Animations**: Implements `Crossfade` transitions between states and high-performance **Scale & Fade-in** animations for grid items using `graphicsLayer`.

---

## How to Run

To test the different implementations, you can switch between the two application modules in Android Studio:

1. Locate the **Run/Debug Configurations** dropdown in the top toolbar (next to the 'Run' icon).
2. Select **`app-xml`** to launch the legacy View-based version.
3. Select **`app-compose`** to launch the modern Jetpack Compose version.
4. Click the **Run** button to deploy the selected module to your device or emulator.

---

## Design Aesthetic
Both modules share the **'Modern Beige'** design language (Walnut/Cream palette), ensuring that the brand identity remains consistent regardless of the underlying UI framework.
