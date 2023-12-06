import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.yifeplayte.mono"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yifeplayte.mono"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        applicationVariants.configureEach {
            outputs.configureEach {
                if (this is BaseVariantOutputImpl) {
                    outputFileName = outputFileName.replace("app", rootProject.name)
                        .replace(Regex("debug|release"), versionName)
                }
            }
        }
    }

    buildTypes {
        named("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro")
        }
        named("debug") {
            versionNameSuffix = "-debug-" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                .format(LocalDateTime.now())
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}
