FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY kuber-app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]