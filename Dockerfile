FROM eclipse-temurin:17-jdk-jammy AS build
USER root

ARG CODECOV_TOKEN

# Install Git and JaCoCo
RUN apt-get update && \
    apt-get install -y git && \
    wget https://repo1.maven.org/maven2/org/jacoco/jacoco-maven-plugin/0.8.8/jacoco-maven-plugin-0.8.8.jar -P /jacoco/

# Install maven
RUN apt-get update && apt-get install -y maven
RUN apt-get update && apt-get install -y python3
# RUN apt-get update && apt-get install -y apt-utils


# Set up working directory
WORKDIR /app

# Copy project files
COPY . .
# COPY mvn pom.xml ./

RUN ls

# Run the tests with JaCoCo
RUN mvn clean test jacoco:report

# RUN curl -s https://codecov.io/bash | bash -s -- -t $CODECOV_TOKEN
RUN curl -Os https://uploader.codecov.io/latest/linux/codecov

RUN chmod +x codecov
RUN ./codecov -t $CODECOV_TOKEN -r EdwardNee/CodeCommenterREST

# Build the Spring Boot app
RUN mvn clean package

# Create a new Docker image for the app
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/commenter-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that the app will listen on
# EXPOSE 8080

# Run the app
CMD ["java", "-jar", "app.jar"]

