plugins {
    java
    `java-library`
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:4.0.0")
    }
}

dependencies {
    implementation(project(":kuber-domain"))
    implementation(project(":kuber-security"))
    implementation(project(":kuber-common"))
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("com.zaxxer:HikariCP:7.0.2")
    api("org.springframework.boot:spring-boot-starter-cache")
}