@file:Suppress("UnstableApiUsage")

import Versions.COMPILE_SDK_VERSION
import Versions.MAX_SDK_VERSION
import Versions.COMPILER_VERSION
import Versions.MIN_SDK_VERSION
import com.simon.harrypotter.Dependencies.TOASTY
import Versions.BUILDTOOLS_VERSION
import com.simon.harrypotter.Dependencies.TIMBER
import com.simon.harrypotter.Dependencies.GSON
import com.simon.harrypotter.androidCompose
import com.simon.harrypotter.coilDependencies
import com.simon.harrypotter.roomDependencies
import com.simon.harrypotter.accompanistDependencies
import com.simon.harrypotter.coroutinesDependecnies
import com.simon.harrypotter.koinDependencies
import com.simon.harrypotter.androidTest

plugins {
    id("com.android.application")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.simon.harrypotter"
    compileSdk = COMPILE_SDK_VERSION

    buildToolsVersion = BUILDTOOLS_VERSION

    defaultConfig {
        applicationId = "com.simon.harrypotter"
        minSdk = MIN_SDK_VERSION
        targetSdk = MAX_SDK_VERSION
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11

        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = COMPILER_VERSION
    }
    packagingOptions {

        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":data"))
    androidCompose()
    androidTest()
    koinDependencies()
    coroutinesDependecnies()
    roomDependencies()
    accompanistDependencies()
    coilDependencies()

    implementation(GSON)

    implementation(TOASTY)
    implementation(TIMBER)


}