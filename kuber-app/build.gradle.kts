plugins {
    id("org.springframework.boot") version "4.0.0"
}

dependencies {
    implementation(project(":kuber-api"))
    implementation("org.flywaydb:flyway-core:11.19.0")
    implementation("org.flywaydb:flyway-mysql:11.19.0")
    runtimeOnly("com.mysql:mysql-connector-j:9.5.0")
    runtimeOnly("org.postgresql:postgresql:42.7.4")
}

tasks.bootRun {
    args("--spring.profiles.active=dev")
}