#!/bin/bash

IMAGE_NAME="algo-mi-api"

# Gradle을 사용하여 프로젝트를 빌드합니다.
./gradlew clean build

# Docker 이미지를 빌드합니다.
docker build -t ${IMAGE_NAME} .

# 호스트의 로컬 디스크를 컨테이너 내부에 마운트하고 Docker 컨테이너를 실행합니다.
docker run -d -p 3000:3000 -v /Users/iyeong-gyo/Desktop/file:/path/to/upload/directory ${IMAGE_NAME}
