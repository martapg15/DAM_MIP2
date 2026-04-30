# 12 – Module Dependency Diagram

The following diagram represents the high-level dependency structure of the Dog Image Browser project after the MIP-3 refactoring.

## Dependency Graph

```mermaid
graph TD
    %% Define Modules
    Core[":core module (Android Library)"]
    AppXML[":app-xml module (Android Application)"]
    AppCompose[":app-compose module (Android Application)"]

    %% Define Dependencies
    AppXML -->|implementation| Core
    AppCompose -->|implementation| Core

    %% Module Responsibilities
    subgraph Shared Business Logic
        Core
    end

    subgraph User Interface Layers
        AppXML
        AppCompose
    end

    %% Legend/Details
    classDef core fill:#f9f,stroke:#333,stroke-width:2px;
    classDef app fill:#bbf,stroke:#333,stroke-width:2px;
    class Core core;
    class AppXML app;
    class AppCompose app;
```

---

## Architecture Summary
- **Unidirectional Flow**: Both application modules point strictly towards the `:core` module.
- **Zero Circular Dependencies**: There is no dependency between `:app-xml` and `:app-compose`.
- **Decoupled Business Logic**: The `:core` module has no awareness of the UI frameworks (XML or Compose) that consume it, ensuring a clean separation of concerns and making the project future-proof for additional UI modules.
