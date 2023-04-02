import Versions.COMPILE_SDK_VERSION
import Versions.MAX_SDK_VERSION
import Versions.MIN_SDK_VERSION
import Versions.BUILDTOOLS_VERSION
import com.simon.harrypotter.Dependencies.TIMBER
import com.simon.harrypotter.Dependencies.GSON
import com.simon.harrypotter.datastoreDependecnies
import com.simon.harrypotter.roomDependencies
import com.simon.harrypotter.ktorDependencies
import com.simon.harrypotter.coroutinesDependecnies
import com.simon.harrypotter.koinDependencies
import com.simon.harrypotter.androidTest


plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.serialization")
}


android {
    namespace = "com.simon.data"
    compileSdk = COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = MIN_SDK_VERSION
        targetSdk = MAX_SDK_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildToolsVersion = BUILDTOOLS_VERSION
}

dependencies {
    androidTest()

    ktorDependencies()

    //Gson
    implementation(GSON)

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    coroutinesDependecnies()
    datastoreDependecnies()
    roomDependencies()
    implementation(TIMBER)

    koinDependencies()

}