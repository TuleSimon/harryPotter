@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        maven ("https://jitpack.io")
        maven ("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
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
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
        mavenCentral()
    }
}

rootProject.name = "HarryPotter"
include(":app",":data")
