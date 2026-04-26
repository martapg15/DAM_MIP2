# 05 – Navigation

## Overview

The Dog Image Browser is a **single-activity application**. There is no multi-screen navigation, back stack management, or Navigation Component configuration required.

---

## Activity

| Attribute | Detail |
|-----------|--------|
| **Activity** | `MainActivity` |
| **Package** | `com.example.mip2` |
| **Layout** | `res/layout/activity_main.xml` |
| **Launcher** | Yes – this is the app entry point |
| **Role** | Sole screen; hosts the dog image viewer UI |

---

## Navigation Map

```
App Launch
    │
    ▼
MainActivity  ◄──── (only screen, no navigation away)
    │
    │  User taps "Refresh"
    ▼
(Same screen updates with a new image)
```

---

## AndroidManifest.xml Configuration

`MainActivity` must be declared as the launcher activity in `AndroidManifest.xml`:

```xml
<activity
    android:name=".MainActivity"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

---

## Future Navigation Considerations

> **Out of scope for this version.**

If the app were to be extended (e.g., breed browsing, favourites), the following approach would be recommended:

- Use the **Jetpack Navigation Component** with a `NavHostFragment`.
- Define a `nav_graph.xml` with appropriate destinations.
- Use **Safe Args** for type-safe argument passing between fragments.

For now, none of this is implemented.
