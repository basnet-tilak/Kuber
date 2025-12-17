@file:Suppress("UnstableApiUsage")

rootProject.name = "Kuber"

include(
    "kuber-common",
    "kuber-domain",
    "kuber-repository",
    "kuber-service",
    "kuber-security",
    "kuber-api",
    "kuber-app"
)

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven(url = "https://repo.spring.io/milestone")
    }
}

