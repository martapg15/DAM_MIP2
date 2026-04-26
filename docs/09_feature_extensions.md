# Feature Extension: Grid Layout, Breed Filter, and Image Scaling

## Feature Name and Short Description
**Grid Layout, Breed Filter, and Image Scaling**

We are upgrading the Dog Image Browser by replacing the single dog view with a dynamic grid layout displaying multiple dogs at once. Additionally, users will be able to filter the displayed dogs by specific breeds. To ensure a polished and visually appealing interface, all images in the grid will be properly scaled and cropped to prevent any stretching or distortion.

## Expected UI Changes
- **Breed Selection UI**: A breed filter interface (such as a Material `Spinner` or a horizontal `RecyclerView` filled with breed chips) will be added to the top of the interface, allowing users to effortlessly select their preferred dog breed.
- **Grid Layout implementation**: The single `ShapeableImageView` will be replaced entirely by a `RecyclerView`. This view will utilize a `GridLayoutManager` configured for 2 or 3 columns to natively support a gallery-like grid presentation.
- **Image Scaling**: To guarantee that images fit seamlessly into their square grid cells without warping, the `ShapeableImageView` instances used within the `RecyclerView` will use `android:scaleType="centerCrop"`. 
- **Retaining Refresh Function**: The refresh button will remain (either as a FAB or integrated button), allowing users to request a fresh batch of images based on the currently selected breed.

## Implementation Tasks

The implementation will be executed systematically following strict MVVM principles:

### 1. Update the Network Layer
- Review the Dog CEO API documentation to confirm the exact endpoints for:
  - Fetching a list of all breeds (`/api/breeds/list/all`).
  - Fetching multiple random dog images (`/api/breeds/image/random/{count}`).
  - Fetching multiple random images for a specific breed (`/api/breed/{breed}/images/random/{count}`).
- Add these new endpoints to the `DogApiService` Retrofit interface.
- Create an additional data model, if necessary, exclusively for the breed list response.

### 2. Update the Repository
- Extend the `DogRepository` to include functions that interact with the new endpoints:
  - `getBreedList()`
  - `getMultipleRandomDogs(count: Int)`
  - `getDogsByBreed(breed: String, count: Int)`

### 3. Implement ViewModel Logic
- Introduce new state holders (`LiveData` & `MutableLiveData`) in `DogViewModel` to handle:
  - The list of available breeds (`List<String>`).
  - The list of currently displayed dog images (`List<String>`).
  - The currently selected breed.
- Update the fetch logic to clear the previous list, trigger the loading state, and fetch either random dogs or breed-specific dogs depending on the user's selection.

### 4. Create the Grid Item Layout
- Create a new XML layout file (e.g., `item_dog_image.xml`) representing a single cell in the grid.
- Inside the layout, employ a `ShapeableImageView` (reusing our rounded image styles).
- Critically, set `android:scaleType="centerCrop"` and configure constraints to force a 1:1 aspect ratio so the images are perfectly square and not stretched.

### 5. Update the Main UI XML
- Refactor `activity_main.xml`.
- Add the specialized Breed selection UI at the top.
- Remove the central `ShapeableImageView` and replace it with a `RecyclerView`.
- Provide proper constraints and margins to respect the Beige Material 3 theme.

### 6. Connect the UI
- Implement a `RecyclerView.Adapter` (`DogImageAdapter`) equipped with Glide to load URLs efficiently into our item layout.
- Bind the ViewModel's `LiveData` to the adapter in `MainActivity`.
- Listen for breed selection events and hook the refresh button up to trigger a new batch fetch depending on state.
