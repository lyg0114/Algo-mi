# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# 볼륨 마운트를 위한 디렉토리 생성
RUN mkdir -p /path/to/upload/directory
# 볼륨 마운트 포인트 설정
VOLUME /path/to/upload/directory

# Copy the JAR file into the container at /app
COPY build/libs/*.jar /app/app.jar

# Expose port 3000 to the outside world
EXPOSE 3000

# Command to run the application with the specified profile
CMD ["java", "-jar", "/app/app.jar", "--spring.profiles.active=prod"]
