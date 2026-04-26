# AI Agent Guidelines – MIP2 Dog Image Browser

## Approach

This project follows a **planning-first** approach. The AI agent must always read and understand the full project documentation before generating any code.

---

## Rules

### 1. Read Documentation First

Before generating any code or suggesting changes, the agent **must** read all files in the `/docs` directory, in order:

1. `docs/01_overview.md`
2. `docs/02_features.md`
3. `docs/03_screens.md`
4. `docs/04_data_model.md`
5. `docs/05_navigation.md`
6. `docs/06_architecture.md`
7. `docs/07_api_usage.md`
8. `docs/08_implementation_plan.md`

> **No code must be generated without first reading the documentation.**

---

### 2. Follow the Defined Architecture

All generated code must strictly conform to the architecture described in `docs/06_architecture.md`:

- **Model** → Kotlin data classes only.
- **ViewModel** → Holds UI state and performs API calls; no Android framework imports except `ViewModel` and `LiveData`.
- **View** → `Activity` classes that observe `LiveData`; all UI elements declared in XML.

---

### 3. Kotlin Only

- Generate **Kotlin** code exclusively.
- Do **not** generate Java code.
- Use idiomatic Kotlin conventions (data classes, extension functions, coroutines when applicable).

---

### 4. UI Must Use XML Views

- All layouts must be written in **XML** (placed under `res/layout/`).
- Do **not** use Jetpack Compose.
- Follow Material Design guidelines for component naming and styling.

---

### 5. Do Not Generate Large Files at Once

- Break code generation into **small, focused steps**.
- Generate one file or one logical unit at a time.
- After each file, pause and verify alignment with the implementation plan.

---

### 6. Follow the Implementation Plan Step by Step

Code must be generated in the exact order defined in `docs/08_implementation_plan.md`:

| Step | Task |
|------|------|
| 1    | Setup project and dependencies (Retrofit, Glide) |
| 2    | Create the Data Model (`DogResponse`) |
| 3    | Define the API interface (`DogApiService`) |
| 4    | Implement Repository and ViewModel |
| 5    | Design the XML layout |
| 6    | Connect UI to ViewModel in `MainActivity` |

> Do **not** skip steps or reorder them.

---

## Summary

| Rule | Description |
|------|-------------|
| Planning-first | Always read `/docs` before writing code |
| Architecture | Strict MVVM as per `06_architecture.md` |
| Language | Kotlin only, no Java, no Compose |
| UI | XML Views only |
| Code size | Small, incremental files |
| Order | Follow `08_implementation_plan.md` exactly |
