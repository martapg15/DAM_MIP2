# 02 – Features

## Feature List

The Dog Image Browser provides a focused set of features designed around a single user interaction loop: **request → display → repeat**.

---

### Feature 1 – Fetch a Random Dog Image

| Attribute | Detail |
|-----------|--------|
| **ID** | F01 |
| **Name** | Fetch Random Dog Image |
| **Description** | On app launch, the app automatically fetches a random dog image from the Dog CEO API. |
| **Trigger** | App starts (`MainActivity.onCreate`) |
| **API Call** | `GET https://dog.ceo/api/breeds/image/random` |
| **Success** | The image URL is extracted from the response and passed to the UI layer. |
| **Failure** | An error state is shown (e.g., a toast message or placeholder). |

---

### Feature 2 – Display the Dog Image

| Attribute | Detail |
|-----------|--------|
| **ID** | F02 |
| **Name** | Display Dog Image |
| **Description** | The fetched image is displayed in a full-width `ImageView` centered on the screen. |
| **Component** | `ImageView` in `activity_main.xml` |
| **Library** | Glide (handles URL loading, caching, and placeholder display) |
| **Placeholder** | A neutral loading indicator is shown while the image downloads. |

---

### Feature 3 – Refresh Button

| Attribute | Detail |
|-----------|--------|
| **ID** | F03 |
| **Name** | Refresh / Load New Image |
| **Description** | A clearly labelled **"Refresh"** button allows the user to request a new random image at any time. |
| **Component** | `Button` in `activity_main.xml` |
| **Trigger** | User taps the Refresh button |
| **Behaviour** | Triggers the same API call as Feature F01, replacing the currently displayed image. |

---

## Out of Scope

The following features are explicitly **not** part of this project:

- Saving / favouriting images.
- Browsing by breed.
- Offline caching.
- User authentication.
- Multiple screens or navigation.

---

## Feature Dependency Map

```
App Launch
    └── F01: Fetch Random Image
            └── F02: Display Image

User Taps Refresh
    └── F01: Fetch Random Image (re-triggered)
            └── F02: Display Image (updated)
```
