# --- Stage 1: Build the JAR ---
# CHANGED: Using 'temurin-21' instead of 'temurin-17'
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy the project files
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# --- Stage 2: Run the JAR ---
# CHANGED: Using 'temurin:21' to match the build version
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy the built JAR from Stage 1
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]