#!/bin/bash

echo "🧹 Cleaning previous build artifacts..."
./gradlew clean || { echo "❌ Gradle clean failed"; exit 1; }

echo "🐳 Cleaning Docker resources..."
docker-compose down --volumes --remove-orphans
docker system prune -f

echo "⚙️ Building JAR file..."
./gradlew :kuber-app:build || { echo "❌ Build failed"; exit 1; }

echo "🐳 Building and starting Docker containers..."
docker-compose up --build