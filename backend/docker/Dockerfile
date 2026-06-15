
# FRONTEND COMPILATION (REACT)

FROM node:20-alpine AS frontend-builder
WORKDIR /app

# Copy frontend dependency configurations
COPY frontend/package*.json ./
RUN npm install

# Copy frontend source code and build the production assets
COPY frontend/ ./
RUN npm run build


# BACKEND COMPILATION (SPRING BOOT)

FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /project

# Copy Maven configuration file from backend folder
COPY backend/pom.xml .
RUN mvn dependency:go-offline

# Copy backend source code
COPY backend/src ./src

# Copy frontend build into backend static resources
COPY --from=frontend-builder /app/build/client/ ./src/main/resources/static/new/

# Compile the project and download libraries
RUN mvn -B package -DskipTests


# EXECUTION IMAGE

FROM eclipse-temurin:21-jre
WORKDIR /usr/src/app/

# Copy the generated JAR file from the builder stage
COPY --from=builder /project/target/*.jar /usr/src/app/app.jar

# Expose the application secure port
EXPOSE 8443

# Command to execute the Spring Boot application
CMD ["java", "-jar", "app.jar"]