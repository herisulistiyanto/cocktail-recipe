package common

import BuildAndroidConfig
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidBasePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val extension = target.extensions.getByName("android")
        if (extension is BaseExtension) {
            extension.apply {
                compileSdkVersion(BuildAndroidConfig.COMPILE_SDK)

                defaultConfig {
                    minSdk = BuildAndroidConfig.MIN_SDK
                    targetSdk = BuildAndroidConfig.TARGET_SDK

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }
            }
        }
    }
}