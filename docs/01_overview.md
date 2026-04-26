# 01 – App Overview

## Purpose

**Dog Image Browser** is an Android application built as part of the **MIP2** (Mobile and Interactive Programming 2) assignment in the LEIM/DAM curriculum.

The primary goal of this project is **educational**: it serves as a hands-on introduction to **AI-assisted Android development**, demonstrating how an AI agent can be guided to generate structured, maintainable Kotlin code when given clear documentation and architectural constraints.

By the end of this project, students will have experience with:

- Consuming a public REST API in Android using **Retrofit**.
- Loading and displaying remote images with **Glide**.
- Structuring an Android app using the **MVVM** architectural pattern.
- Writing clean, idiomatic **Kotlin** code.
- Working within an **AI-assisted, documentation-first** development workflow.

---

## Application Description

The app allows users to browse random dog images fetched live from the **Dog CEO API** (`https://dog.ceo/dog-api`). Each time the user taps the refresh button, a new random image is retrieved and displayed on screen.

The interface is intentionally minimal, focusing on clarity and correctness of the underlying architecture rather than visual complexity.

---

## Target Users

| User Group | Description |
|------------|-------------|
| **Students** | LEIM/DAM students learning mobile development and AI-assisted coding workflows. |
| **Instructors** | Teaching staff reviewing the project structure and code quality. |
| **AI Agents** | Automated agents following these docs to generate the implementation step by step. |

---

## Learning Objectives

1. Understand how to integrate a third-party REST API into an Android app.
2. Apply the MVVM pattern to separate concerns between UI and business logic.
3. Use documentation-driven development to guide AI code generation.
4. Write Kotlin data classes, interfaces, and ViewModels from a defined specification.

---

## Scope

This project covers a **single-screen Android application**. There is no login, database, or offline caching in scope. The focus is entirely on the network-to-UI data flow.
