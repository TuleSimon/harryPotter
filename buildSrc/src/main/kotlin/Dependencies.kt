package com.simon.harrypotter

import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

  const val ANDROID_CORE_KTS = "androidx.core:core-ktx:1.9.0"
  const val ANDROID_LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.0"
  const val ANDROIDX_ACTIVITY_COMPOSE = "androidx.activity:activity-compose:1.6.1"


  //compose
  const val ANDROIDX_COMPOSE_UI = "androidx.compose.ui:ui:${Versions.COMPOSE_VERSION}"
  const val ANDROIDX_COMPOSE_UI_TOOLING_PREVIEW =
    "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE_VERSION}"
  const val ANDROIDX_COMPOSE_MATERIAL3 = "androidx.compose.material3:material3:1.1.0-alpha07"
  const val ANDROIDX_VIEWMODEL_COMPOSE = "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0"
  const val ANDROIDX_MATERIAL_ICONS_EXTENDED = "androidx.compose.material:material-icons-extended:${Versions.COMPOSE_VERSION}"
  const val ANDROIDX_COMPOSE_VIEWBINDING = "androidx.compose.ui:ui-viewbinding:${Versions.COMPOSE_VERSION}"
  const val ANDROIDX_COMPOSE_UI_UTIL = "androidx.compose.ui:ui-util:1.3.3"
  const val ANDROIDX_NAVIGATION_COMPOSE = "androidx.navigation:navigation-compose:${Versions.NAVIGATION_VERSION}"
  //constraint layout
  const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout-compose:1.0.1"
  //livedata
  const val LIVEDATA = "androidx.compose.runtime:runtime-livedata:1.4.0-rc01"


  //koin
  // Koin Core features
  const val KOIN_CORE =  "io.insert-koin:koin-core:${Versions.KOIN_VERSION}"
  // Koin Test features
  const val KOIN_TEST_IMPLEMENTATION =  "io.insert-koin:koin-test:${Versions.KOIN_VERSION}"
  // Koin main features for Android
  const val KOIN_ANDROID_VERSION =  "io.insert-koin:koin-android:${Versions.KOIN_ANDROID_VERSION}"
  // Java Compatibility
  const val KOIN_ANDROID_COMPAT = "io.insert-koin:koin-android-compat:${Versions.KOIN_ANDROID_VERSION}"
  // Jetpack WorkManager
  const val KOIN_WORK_MANAGER =  "io.insert-koin:koin-androidx-workmanager:${Versions.KOIN_ANDROID_VERSION}"
  // Navigation Graph
  const val KOIN_ANDROIDX_NAVIGATION =  "io.insert-koin:koin-androidx-navigation:${Versions.KOIN_ANDROID_VERSION}"
  // Jetpack Compose
  const val KOIN_ANDRIDX_COMPOSE =  "io.insert-koin:koin-androidx-compose:${Versions.ANDROID_COMPOSE_VERSION}"

  const val JUNIT = "junit:junit:4.13.2"
  const val ANDROIDX_JUNIT_TEST = "androidx.test.ext:junit:1.1.5"
  const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.5.1"
  const val ANDROIDX_COMPOSE_UI_TEST_JUNIT4 =
    "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE_VERSION}"
  const val ANDROIDX_COMPOSE_UI_TOOLING =
    "androidx.compose.ui:ui-tooling:${Versions.COMPOSE_VERSION}"
  const val ANDROIDX_COMPOSE_UI_MANIFEST_TEST =
    "androidx.compose.ui:ui-test-manifest:${Versions.COMPOSE_VERSION}"


  // Coroutines
  const val KOLTINX_COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4"
  const val ANDROIDX_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0"
  const val KOTLINX_COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"



  // Room
  const val ANDROIDX_ROOM_RUNTIME = "androidx.room:room-runtime:2.5.0"
  const val ANDROIDX_ROOM_KAPT = "androidx.room:room-compiler:2.5.0"
  // Kotlin Extensions and Coroutines support for Room
  const val ANDROIDX_R0OM = "androidx.room:room-ktx:2.5.0"


  //Splash Screen
  const val ANDROIDX_SPLASHSCREEN = "androidx.core:core-splashscreen:1.0.0"
  const val ANDROIDX_LEGACY_SUPPORT = "androidx.legacy:legacy-support-v4:1.0.0"

  // coroutines for getting off the UI thread
  const val KOTLIN_REFLECT  = "org.jetbrains.kotlin:kotlin-reflect:1.8.10"

  // Jetpack lifecycle components
  const val ANDROIDX_LIFECYCLER_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_VERSION}"
  const val ANDROIDX_LIFECYCLE_COMMON = "androidx.lifecycle:lifecycle-common-java8:${Versions.LIFECYCLE_VERSION}"
  const val ANDROIDX_LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_VERSION}"
  const val ANDROIDX_LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:2.5.1"

  // accompanist
  const val  ACCOMPANIST = "com.google.accompanist:accompanist-pager:0.19.0"
  const val ACCOMPANIST_PERMISSON = "com.google.accompanist:accompanist-permissions:0.28.0"
  const val ACCOMPANIST_PAGER = "com.google.accompanist:accompanist-pager:0.26.3-beta"
  // If using indicators, also depend on
  const val ACCOMPANIST_PAGER_INDICATOR = "com.google.accompanist:accompanist-pager-indicators:0.25.0"
  const val NAVIGATION_ANIMATION = "com.google.accompanist:accompanist-navigation-animation:0.27.0"
  const val ACCOMPANIST_SYSTEMUICONTROLLER = "com.google.accompanist:accompanist-systemuicontroller:0.17.0"
  const val ACCOMPANIST_SWIPEREFRESH = "com.google.accompanist:accompanist-swiperefresh:0.24.13-rc"
  const val ACCOMPANIST_PLACEHOLDER = "com.google.accompanist:accompanist-placeholder-material:0.27.1"


  // Coil
  const val COIL_COMPOPSE = "io.coil-kt:coil-compose:2.2.2"


  //ktor
  const val KTOR_CLIENT_ANDROID = "io.ktor:ktor-client-android:${Versions.KTOR_VERSION}"
  const val KTOR_SERIALIZATION = "io.ktor:ktor-client-serialization:${Versions.KTOR_VERSION}"
  const val KTOR_CLIENT_OKHTTP =  "io.ktor:ktor-client-okhttp:${Versions.KTOR_VERSION}"
// Necessary to show logs in logcat
  const val KTOR_CLIENT_CORE = "io.ktor:ktor-client-core:${Versions.KTOR_VERSION}"
  const val KTOR_CLIENT_WEBSOCKETS = "io.ktor:ktor-client-websockets:${Versions.KTOR_VERSION}"
  const val CONTENT_NEGOTIATION = "io.ktor:ktor-client-content-negotiation:2.0.0"
  const val KTOR_KOTLINX_SERILIZATION = "io.ktor:ktor-serialization-kotlinx-json:${Versions.KTOR_VERSION}"
  const val KTOR_CLIENT_LOGGING =  "io.ktor:ktor-client-logging-jvm:${Versions.KTOR_VERSION}"
  const val KTOR_CLIENT_AUTH = "io.ktor:ktor-client-auth-jvm:2.0.0"




  //Gson
  const val GSON = "com.google.code.gson:gson:2.10.1"

  // data store
  const val DATASTORE_CORE = "androidx.datastore:datastore-preferences-core:1.0.0"
  const val ANDROIDX_DATASTORE =  "androidx.datastore:datastore-preferences:1.0.0"

  const val TOASTY = "com.github.GrenderG:Toasty:1.5.2"
  const val TIMBER = "com.jakewharton.timber:timber:5.0.1"

}

// android test dependencies
fun DependencyHandler.androidTest() {
  testImplementation(Dependencies.JUNIT)
  androidTestImplementation(Dependencies.ANDROIDX_JUNIT_TEST)
  androidTestImplementation(Dependencies.ESPRESSO_CORE)
  androidTestImplementation(Dependencies.ANDROIDX_COMPOSE_UI_TEST_JUNIT4)
  debugImplementation(Dependencies.ANDROIDX_COMPOSE_UI_TOOLING)
  debugImplementation(Dependencies.ANDROIDX_COMPOSE_UI_MANIFEST_TEST)
}

fun DependencyHandler.koinDependencies() {

  implementation(Dependencies.KOIN_CORE)
  implementation(Dependencies.KOIN_ANDROID_VERSION)
  implementation(Dependencies.KOIN_ANDROID_COMPAT)
  implementation(Dependencies.KOIN_WORK_MANAGER)
  implementation(Dependencies.KOIN_ANDRIDX_COMPOSE)
  implementation(Dependencies.KOIN_ANDROIDX_NAVIGATION)
  testImplementation(Dependencies.KOIN_TEST_IMPLEMENTATION)
}

fun DependencyHandler.coroutinesDependecnies() {
  implementation(Dependencies.KOLTINX_COROUTINES)
  implementation(Dependencies.ANDROIDX_VIEWMODEL)
  implementation(Dependencies.KOTLINX_COROUTINES_ANDROID)

}

fun DependencyHandler.ktorDependencies() {
  implementation(Dependencies.KTOR_CLIENT_ANDROID)
  implementation(Dependencies.KTOR_CLIENT_OKHTTP)
  implementation(Dependencies.KTOR_SERIALIZATION)
  implementation(Dependencies.KTOR_CLIENT_CORE)
  implementation(Dependencies.KTOR_CLIENT_WEBSOCKETS)
  implementation(Dependencies.CONTENT_NEGOTIATION)
  implementation(Dependencies.KTOR_KOTLINX_SERILIZATION)
  implementation(Dependencies.KTOR_CLIENT_LOGGING)
  implementation(Dependencies.KTOR_CLIENT_AUTH)

}

fun DependencyHandler.datastoreDependecnies() {
  implementation(Dependencies.DATASTORE_CORE)
  implementation(Dependencies.ANDROIDX_DATASTORE)
}

fun DependencyHandler.roomDependencies() {
  implementation(Dependencies.ANDROIDX_ROOM_RUNTIME)
  implementation(Dependencies.ANDROIDX_R0OM)
  kapt(Dependencies.ANDROIDX_ROOM_KAPT)
}


fun DependencyHandler.accompanistDependencies() {
  // accompanist
  implementation(Dependencies.ACCOMPANIST)
  implementation(Dependencies.ACCOMPANIST_PERMISSON)
  implementation(Dependencies.ACCOMPANIST_PAGER)
  implementation(Dependencies.ACCOMPANIST_PAGER_INDICATOR)
  implementation(Dependencies.ACCOMPANIST_SYSTEMUICONTROLLER)
  implementation(Dependencies.ACCOMPANIST_SWIPEREFRESH)
  implementation(Dependencies.ACCOMPANIST_PLACEHOLDER)
  implementation(Dependencies.NAVIGATION_ANIMATION)
}

fun DependencyHandler.coilDependencies() {
  implementation(Dependencies.COIL_COMPOPSE)
}

// compose ui dependecies
fun DependencyHandler.androidCompose() {

  implementation(Dependencies.ANDROID_CORE_KTS)
  implementation(Dependencies.ANDROID_LIFECYCLE_RUNTIME_KTX)
  implementation(Dependencies.ANDROIDX_ACTIVITY_COMPOSE)
  implementation(Dependencies.ANDROIDX_COMPOSE_UI)
  implementation(Dependencies.ANDROIDX_COMPOSE_UI_TOOLING_PREVIEW)
  implementation(Dependencies.ANDROIDX_COMPOSE_MATERIAL3)
  implementation(Dependencies.ANDROIDX_VIEWMODEL_COMPOSE)
  implementation(Dependencies.ANDROIDX_MATERIAL_ICONS_EXTENDED)
  implementation(Dependencies.ANDROIDX_COMPOSE_VIEWBINDING)
  implementation(Dependencies.ANDROIDX_COMPOSE_UI_UTIL)
  implementation(Dependencies.ANDROIDX_NAVIGATION_COMPOSE)
  implementation(Dependencies.CONSTRAINT_LAYOUT)
  implementation(Dependencies.ANDROIDX_SPLASHSCREEN)
  implementation(Dependencies.ANDROIDX_LEGACY_SUPPORT)
  implementation(Dependencies.KOTLIN_REFLECT)
  implementation(Dependencies.ANDROIDX_LIFECYCLER_RUNTIME)
  implementation(Dependencies.ANDROIDX_LIFECYCLE_COMMON)
  implementation(Dependencies.ANDROIDX_LIFECYCLE_VIEWMODEL)
  implementation(Dependencies.ANDROIDX_LIFECYCLE_LIVEDATA)
  implementation(Dependencies.LIVEDATA)

}

fun DependencyHandler.koin(){
  implementation(Dependencies.KOIN_CORE)
}

fun DependencyHandler.implementation(depName: String) {
  add("implementation", depName)
}

private fun DependencyHandler.kapt(depName: String) {
  add("kapt", depName)
}

private fun DependencyHandler.compileOnly(depName: String) {
  add("compileOnly", depName)
}

private fun DependencyHandler.api(depName: String) {
  add("api", depName)
}

private fun DependencyHandler.debugImplementation(depName: String) {
  add("debugImplementation", depName)
}

private fun DependencyHandler.androidTestImplementation(depName: String) {
  add("androidTestImplementation", depName)
}

private fun DependencyHandler.testImplementation(depName: String) {
  add("testImplementation", depName)
}
