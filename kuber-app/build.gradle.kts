plugins {
    id("org.springframework.boot") version "4.0.0"
    id("io.spring.dependency-management") version "1.1.7"
    java
}

dependencies {
    implementation(project(":kuber-api"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.flywaydb:flyway-core:11.19.0")
    implementation("org.flywaydb:flyway-mysql:11.19.0")
    runtimeOnly("com.mysql:mysql-connector-j:9.5.0")
    runtimeOnly("org.postgresql:postgresql:42.7.4")
}

tasks.bootRun {
    args("--spring.profiles.active=dev")
}

tasks.bootJar {
    enabled = true
    archiveClassifier = ""
}

tasks.jar {
    enabled = false
}