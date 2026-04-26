# MIP2 – Dog Image Browser

## Overview

**Dog Image Browser** is an Android application developed as part of the **MIP2** assignment for the LEIM/DAM course. The app lets users fetch and display random dog images from the internet, serving as a practical introduction to AI-assisted mobile development with Kotlin.

## API Used

This app consumes the **[Dog CEO API](https://dog.ceo/dog-api)**, a free, open REST API that provides random dog images from hundreds of breeds.

- **Endpoint:** `GET https://dog.ceo/api/breeds/image/random`
- **Response:** JSON with an image URL and a status field.

## Features

- Fetch a random dog image on launch.
- Display the image in a clean, single-screen UI.
- Refresh button to load a new random dog image.

## Architecture

The app follows the **MVVM (Model-View-ViewModel)** pattern:

- **Model** – Kotlin data classes representing the API response.
- **ViewModel** – Handles business logic and API calls via Retrofit.
- **View** – A single `MainActivity` with an XML layout.

## Tech Stack

| Layer        | Library / Tool  |
|--------------|-----------------|
| Language     | Kotlin          |
| UI           | XML Views       |
| Networking   | Retrofit + OkHttp |
| Image Loading| Glide           |
| Architecture | MVVM + LiveData |

## Project Structure

```
MIP2/
├── app/
├── docs/
│   ├── 01_overview.md
│   ├── 02_features.md
│   ├── 03_screens.md
│   ├── 04_data_model.md
│   ├── 05_navigation.md
│   ├── 06_architecture.md
│   ├── 07_api_usage.md
│   └── 08_implementation_plan.md
├── agents.md
└── README.md
```

## Getting Started

### Prerequisites

- Android Studio (Hedgehog or later recommended)
- Android SDK 24+
- Internet connection (for API calls)

### Running the Project

1. Clone or download this repository.
2. Open the project in **Android Studio** (`File > Open > MIP2/`).
3. Wait for Gradle to sync all dependencies.
4. Connect an Android device or start an emulator.
5. Click **Run ▶** (or press `Shift + F10`).

## Documentation

All detailed documentation lives in the `/docs` folder. Read it in order before modifying or extending the project:

1. [Overview](docs/01_overview.md)
2. [Features](docs/02_features.md)
3. [Screens](docs/03_screens.md)
4. [Data Model](docs/04_data_model.md)
5. [Navigation](docs/05_navigation.md)
6. [Architecture](docs/06_architecture.md)
7. [API Usage](docs/07_api_usage.md)
8. [Implementation Plan](docs/08_implementation_plan.md)

## License

This project is for academic purposes only.
