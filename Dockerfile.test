FROM eclipse-temurin:17-jdk-jammy AS build
USER root

ARG CODECOV_TOKEN

# Install Git and JaCoCo
RUN apt-get update && \
    apt-get install -y git && \
    wget https://repo1.maven.org/maven2/org/jacoco/jacoco-maven-plugin/0.8.8/jacoco-maven-plugin-0.8.8.jar -P /jacoco/

# Install maven
RUN apt-get update && apt-get install -y maven && \
    apt-get install -y python3


# Set up working directory
WORKDIR /app

# Copy project files
COPY . .

RUN ls

# Run the tests with JaCoCo
RUN mvn clean test jacoco:report

RUN curl -Os https://uploader.codecov.io/latest/linux/codecov

RUN chmod +x codecov
RUN ./codecov -t $CODECOV_TOKEN -r EdwardNee/CodeCommenterREST

# Build the Spring Boot app
RUN mvn clean package

