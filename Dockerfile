FROM maven:3.8.6-jdk-17-slim as build

# Install Git and JaCoCo
RUN apt-get update && \
    apt-get install -y git && \
    wget https://repo1.maven.org/maven2/org/jacoco/jacoco-maven-plugin/0.8.7/jacoco-maven-plugin-0.8.7.jar -P /jacoco/

# Set up working directory
WORKDIR /app

# Copy project files
COPY . .

# Run the tests with JaCoCo
RUN mvn clean test jacoco:report

# Build the Spring Boot app
RUN mvn clean package

# Create a new Docker image for the app
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/commenter-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that the app will listen on
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "app.jar"]