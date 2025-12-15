@file:Suppress("UnstableApiUsage")


rootProject.name = "Kuber"
include(":app", ":kuber-core","user-services","kuber-web","kuber-security")

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

