./gradlew build
docker build -t auth-app:latest --build-arg JAR_FILE=./build/libs/auth-0.0.1-SNAPSHOT.jar .
docker run --network factura-login-network  --name auth-app auth-app:latest
