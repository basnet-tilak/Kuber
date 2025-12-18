plugins {
    id("org.springframework.boot") version "4.0.0" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    java
    `java-library`
}

allprojects {
    group = "com.kuber"
    version = "0.0.1-SNAPSHOT"
    repositories {
        mavenCentral()
    }
}
subprojects {
    repositories {
        mavenCentral()
        maven(url = "https://repo.spring.io/milestone")
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
    }

    tasks.test {
        useJUnitPlatform()
    }
}