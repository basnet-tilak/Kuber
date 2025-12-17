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
    implementation(project(":kuber-repository"))
    implementation(project(":kuber-domain"))
    implementation(project(":kuber-security"))
    implementation(project(":kuber-common"))
    api("org.springframework.boot:spring-boot-starter-webflux")
    api("org.springframework.boot:spring-boot-starter-security")
    api("io.jsonwebtoken:jjwt-api:0.13.0")
    testImplementation("io.projectreactor:reactor-test")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.13.0")
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
}