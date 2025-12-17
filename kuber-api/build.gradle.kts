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
    implementation(project(":kuber-service"))
    implementation(project(":kuber-common"))
    api("org.springframework.boot:spring-boot-starter-webflux") // Reactive for high concurrency
    api("org.springframework.boot:spring-boot-starter-validation")
    api("org.springframework.boot:spring-boot-starter-actuator") // Monitoring
    api("io.micrometer:micrometer-registry-prometheus") // Metrics
    api("org.springframework.boot:spring-boot-starter-cache") // Caching
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
}