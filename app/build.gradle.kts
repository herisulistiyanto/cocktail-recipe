import deps.Dependencies
import deps.TestDependencies
import utils.ConfigUtil

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("kapt")
    `android-base-plugin`
}

android {
    defaultConfig {
        applicationId = "com.andro.indieschool.cocktailapp"
        versionCode = BuildAndroidConfig.VERSION_CODE
        versionName = BuildAndroidConfig.VERSION_NAME
        ConfigUtil.loadConfigMap("${rootDir}/config.properties") { key, value ->
            buildConfigField("String", key.toUpperCase(), value)
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        viewBinding = true
        dataBinding = false
    }
}

dependencies {
    implementation(Dependencies.SPLASH)
    implementation(Dependencies.APP_COMPAT)
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.LEGACY_SUPPORT)
    implementation(Dependencies.PALETTE)

    implementation(Dependencies.CONSTRAINT_LAYOUT)
    implementation(Dependencies.VIEW_PAGER)
    implementation(Dependencies.FRAGMENT)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.VIEWMODEL_KTX)
    implementation(Dependencies.LIFECYCLE_KTX)
    implementation(Dependencies.COIL)
    implementation(Dependencies.SHIMMER)

    implementation(Dependencies.NAVIGATION_FRAGMENT)
    implementation(Dependencies.NAVIGATION_UI)

    // Coroutine
    implementation(Dependencies.COROUTINE_CORE)
    implementation(Dependencies.COROUTINE_ANDROID)

    // Retrofit
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_GSON)

    // OkHTTP
    implementation(Dependencies.OKHTTP)
    implementation(Dependencies.OKHTTP_LOGGING)

    // KOIN
    implementation(Dependencies.KOIN)
    implementation(Dependencies.KOIN_ANDROID)

    // ROOM
    implementation(deps.Dependencies.ROOM)
    implementation(deps.Dependencies.ROOM_KTX)
    kapt(deps.Dependencies.ROOM_COMPILER)

    // KOTLIN REFLECT
    implementation(deps.Dependencies.KOTLIN_REFLECT)

    // LOTTIE
    implementation(Dependencies.LOTTIE)

    testImplementation(TestDependencies.JUNIT_4)
    androidTestImplementation(TestDependencies.JUNIT_EXT)
    androidTestImplementation(TestDependencies.ESPRESSO_CORE)
    androidTestImplementation(TestDependencies.ESPRESSO_CONTRIB)

    androidTestImplementation(TestDependencies.NAVIGATION_TEST)
    debugImplementation(TestDependencies.FRAGMENT_TEST)
}