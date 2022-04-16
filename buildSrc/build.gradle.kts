plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

gradlePlugin {
    plugins {
        register("android-base-plugin") {
            id = "android-base-plugin"
            implementationClass = "common.AndroidBasePlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2")
}