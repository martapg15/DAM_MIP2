# 03 – Screens

## Screen List

The application consists of a **single screen**: the Main Screen (Home).

---

## Main Screen – Dog Image Viewer

### Overview

| Attribute | Detail |
|-----------|--------|
| **Screen ID** | S01 |
| **Name** | Main Screen |
| **Activity** | `MainActivity` |
| **Layout File** | `res/layout/activity_main.xml` |
| **Purpose** | Display a random dog image and allow the user to refresh it. |

---

### Layout Structure

```
┌─────────────────────────────────────┐
│           Toolbar / AppBar           │
│         "Dog Image Browser"          │
├─────────────────────────────────────┤
│                                     │
│                                     │
│           [ ImageView ]             │
│     (displays the dog image)        │
│                                     │
│                                     │
├─────────────────────────────────────┤
│          [ Refresh Button ]         │
└─────────────────────────────────────┘
```

---

### UI Components

#### 1. Toolbar / AppBar

| Property | Value |
|----------|-------|
| **Type** | `androidx.appcompat.widget.Toolbar` (inside `AppBarLayout`) |
| **ID** | `toolbar` |
| **Title** | `"Dog Image Browser"` |
| **Position** | Top of screen |
| **Background** | Primary colour from the app theme |

---

#### 2. ImageView

| Property | Value |
|----------|-------|
| **Type** | `ImageView` |
| **ID** | `imageViewDog` |
| **Width** | `match_parent` |
| **Height** | `0dp` (constrained, takes remaining vertical space) |
| **Scale Type** | `centerCrop` |
| **Content Description** | `"Random dog image"` |
| **Loaded By** | Glide (in `MainActivity`, observing `ViewModel.dogImageUrl`) |
| **Placeholder** | A neutral drawable while the image loads |

---

#### 3. Refresh Button

| Property | Value |
|----------|-------|
| **Type** | `Button` (Material Design) |
| **ID** | `buttonRefresh` |
| **Text** | `"Refresh"` (string resource `@string/refresh`) |
| **Width** | `wrap_content` |
| **Position** | Bottom-centre of the screen |
| **On Click** | Calls `viewModel.fetchRandomDogImage()` |

---

### States

| State | Description |
|-------|-------------|
| **Loading** | Glide placeholder shown while image downloads |
| **Success** | Dog image rendered in the `ImageView` |
| **Error** | Toast message displayed; ImageView may retain previous image |

---

### Accessibility

- The `ImageView` must have a non-empty `contentDescription`.
- The `Button` text must be descriptive enough to convey its action.
- All interactive elements must have unique IDs to support automated UI testing.
