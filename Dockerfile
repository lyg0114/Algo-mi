# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY build/libs/*.jar /app/app.jar

# Expose port 8080 to the outside world
EXPOSE 3000

# Command to run the application
CMD ["java", "-jar", "app.jar"]
