# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY build/libs/*.jar /app/app.jar

# Expose port 3000 to the outside world
EXPOSE 3000

# Command to run the application with the specified profile
CMD ["java", "-jar", "/app/app.jar", "--spring.profiles.active=prod"]
