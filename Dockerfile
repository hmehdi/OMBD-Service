# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set environment variables
ENV APP_NAME=ombd-service
ENV APP_VERSION=1.0

# Create a directory for the application
RUN mkdir /app

# Set the working directory to /app
WORKDIR /app

COPY target/ombd-service-1.0.jar /app/ombd-service.jar

EXPOSE 8080

# Define the command to run your Spring Boot application
CMD ["java", "-jar", "ombd-service.jar"]