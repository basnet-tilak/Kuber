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
    implementation(project(":kuber-common"))
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
}