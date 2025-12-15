plugins {
    id("org.springframework.boot") version "4.0.0" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    java
    `java-library`
}

subprojects {
    repositories {
        mavenCentral()
        maven(url = "https://repo.spring.io/milestone")
    }
}

allprojects {
    group = "com.kuber"
    version = "0.0.1-SNAPSHOT"
}
