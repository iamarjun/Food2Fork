# Food2Fork 🍲 

**Food2Fork** is a sample food blog 🍲 Android application 📱 built to demonstrate use of *Modern Android development* tools.

## About
It simply loads **Posts** data from API and stores it in persistence storage (i.e. SQLite Database). Posts will be always loaded from local database. Remote data (from API) and Local data is always synchronized. 
- This makes it offline capable 😃. 
- Clean and Simple Material UI.
- It supports dark theme too 🌗.

*API is used in this app.(https://recipesapi.herokuapp.com/api/search?q=chicken)*.

## Screenshots
<img src="https://github.com/iamarjun/Food2Fork/blob/master/screenshots/Screenshot_20200904-130523.png" width="300" >
<img src="https://github.com/iamarjun/Food2Fork/blob/master/screenshots/Screenshot_20200904-130530.png" width="300" >
<img src="https://github.com/iamarjun/Food2Fork/blob/master/screenshots/Screenshot_20200904-130538.png" width="300" >
<img src="https://github.com/iamarjun/Food2Fork/blob/master/screenshots/Screenshot_20200904-130544.png" width="300" >
<img src="https://github.com/iamarjun/Food2Fork/blob/master/screenshots/Screenshot_20200904-130650.png" width="300" >
<img src="https://github.com/iamarjun/Food2Fork/blob/master/screenshots/Screenshot_20200904-130620.png" width="300" >
<img src="https://github.com/iamarjun/Food2Fork/blob/master/screenshots/Screenshot_20200904-130627.png" width="300" >
<img src="https://github.com/iamarjun/Food2Fork/blob/master/screenshots/Screenshot_20200904-130642.png" width="300" >
<img src="https://github.com/iamarjun/Covid19Tracker/blob/master/screenshots/Screenshot_20200904-124719.png" width="300" >


## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) - 
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Kotlin and Java.
- [Moshi Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/moshi) - A Converter which uses Moshi for serialization to and from JSON.
- [Coil-kt](https://coil-kt.github.io/coil/) - An image loading library for Android backed by Kotlin Coroutines.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - For writing Gradle build scripts using Kotlin.


## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)
