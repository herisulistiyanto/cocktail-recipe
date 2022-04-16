import deps.Dependencies
import deps.TestDependencies
import utils.ConfigUtil
import com.android.build.api.artifact.SingleArtifact

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("kapt")
    `android-base-plugin`
}

android {
    val keystoreMap = mutableMapOf<String, String>()
    if (file("$rootDir/secret.properties").exists()) {
        ConfigUtil.loadConfigMap("./secret.properties") { key, value ->
            keystoreMap[key] = value
        }
    }
    signingConfigs {
        getByName("debug") {
            keyAlias = keystoreMap["debug_alias"]
            keyPassword = keystoreMap["debug_pass"]
            storeFile = file("$rootDir/${keystoreMap["debug_store_path"].orEmpty()}")
            storePassword = keystoreMap["debug_store_pass"]
        }
        create("release") {
            keyAlias = keystoreMap["release_alias"]
            keyPassword = keystoreMap["release_pass"]
            storeFile = file("$rootDir/${keystoreMap["release_store_path"].orEmpty()}")
            storePassword = keystoreMap["release_store_pass"]
        }
    }

    defaultConfig {
        applicationId = "com.andro.indieschool.cocktailapp"
        versionCode = BuildAndroidConfig.VERSION_CODE
        versionName = BuildAndroidConfig.VERSION_NAME
        ConfigUtil.loadConfigMap("${rootDir}/config.properties") { key, value ->
            buildConfigField("String", key.toUpperCase(), value)
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
        }
        release {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
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

androidComponents {
    onVariants(selector().all()) { variant ->
        val target = variant.name.capitalize()
        tasks.register("assembleApk$target") {
            group = "build"
            description = "Generate APK with version name for $target variant."
            dependsOn(tasks.findByPath("assemble$target"))
            val apkFile = variant.artifacts.get(SingleArtifact.APK).get().asFile
            val apkName = apkFile.name
            val apkFolder = apkFile.absolutePath
            doLast {
                copy {
                    from(apkFolder) {
                        include("**/*$apkName.apk")
                    }
                    into(apkFolder)
                    rename {
                        "${BuildAndroidConfig.APP_NAME}-${variant.buildType}-${BuildAndroidConfig.VERSION_NAME}.apk"
                    }

                }
            }
        }
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