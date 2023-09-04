# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set environment variables
ENV APP_NAME=ombd-service
ENV APP_VERSION=1.0

# Create a directory for the application
RUN mkdir /app

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file built by Maven to the container
COPY target/ombd-service-1.0.jar /app/ombd-service.jar

# Expose the port that your Spring Boot application will run on (default is 8080)
EXPOSE 8080

# Define the command to run your Spring Boot application
CMD ["java", "-jar", "ombd-service.jar"]