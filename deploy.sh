#!/bin/bash

IMAGE_NAME="algo-mi-api"
TAG_VERSION="latest-v4" # should change version info

./gradlew clean build
docker push whdnseowkd/${IMAGE_NAME}:${TAG_VERSION}

docker build -t ${IMAGE_NAME} .
docker run -p 3000:3000 ${IMAGE_NAME}