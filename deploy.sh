#!/bin/bash

IMAGE_NAME="algo-mi-api"

./gradlew clean build
docker build -t ${IMAGE_NAME} .
docker run -p 3000:3000 ${IMAGE_NAME}