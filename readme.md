## Cocktail App

Sample playground project for learning purpose in modern Android development.

#### Tech stack & libraries
- Minimum SDK 21
- MVVM architecture
- [Kotlin](https://kotlinlang.org/) based.
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - concurrency & asynchronous.
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) - reactive library
- [Coil](https://github.com/coil-kt/coil) Image loading library.
- [KOIN](https://github.com/InsertKoinIO/koin) - kotlin dsl service locator.
- [Retrofit](https://github.com/square/retrofit) - construct REST client.
- [GSON](https://github.com/google/gson) - serialize-deserialize response.
- [Shimmer](https://github.com/facebook/shimmer-android) - shimmer effect for loading placeholder.
- [Lottie](https://github.com/airbnb/lottie-android) - animation library
- [Material Components](https://github.com/material-components/material-components-android) - UI design based.
- [Room](https://developer.android.com/training/data-storage/room) - Persistence library (database)
- [Navigation Component](https://developer.android.com/guide/navigation) - Android navigation library, part of Jetpack.
- [SplashScreen API](https://developer.android.com/guide/topics/ui/splash-screen) - Splash screen API for android

#### Architecture
This app is based on MVVM architecture and Repository pattern. It also use NetworkBoundResource to cache the network response into database for offline support.

#### Screenshots

<p float="left">
  <img src="https://user-images.githubusercontent.com/12156917/163665454-8834cc5a-f392-4ad4-a6df-04e28d2c2364.png" alt="Home" width="32%">
  <img src="https://user-images.githubusercontent.com/12156917/163665692-40671949-9330-4f21-80b6-2ef299bdd596.png" alt="Detail" width="32%">
</p>