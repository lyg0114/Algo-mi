./gradlew clean build
docker build -t algo-mi .
docker run -p 3000:3000 algo-mi