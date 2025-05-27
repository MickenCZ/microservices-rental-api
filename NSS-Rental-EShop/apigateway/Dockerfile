FROM eclipse-temurin:17-jdk-jammy AS build

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Copy source code
COPY . /app
WORKDIR /app

# Build the application
RUN mvn clean package -DskipTests

# ======================
FROM eclipse-temurin:17-jdk-jammy

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]