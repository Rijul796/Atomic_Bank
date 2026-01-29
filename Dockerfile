# --- Stage 1: Build the JAR ---
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy the project files
COPY . .

# Build the application (Skipping tests to save time)
RUN mvn clean package -DskipTests

# --- Stage 2: Run the JAR ---
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the built JAR from Stage 1
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]