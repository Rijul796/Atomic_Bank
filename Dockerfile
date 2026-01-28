# 1. Start with a base OS that has Java 17/21 installed
FROM eclipse-temurin:21-jdk-alpine

# 2. Create a folder inside the container
WORKDIR /app

# 3. Copy your compiled JAR file from your computer to the container
COPY target/atomicbank-0.0.1-SNAPSHOT.jar app.jar

# 4. Tell the container to listen on port 8080
EXPOSE 8080

# 5. The command to run when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]