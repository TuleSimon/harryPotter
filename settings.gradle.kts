pluginManagement {
    repositories {
        google()
        maven ("https://jitpack.io")
        maven ("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven ("https://jitpack.io")
        maven ("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        mavenCentral()
    }
}

rootProject.name = "HarryPotter"
include(":app",":data")
