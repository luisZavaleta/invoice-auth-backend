gradle build --no-build-cache
docker build -f DockerfileLocal -t luiszavaleta/auth-app:local.7 --build-arg JAR_FILE=./build/libs/auth-0.0.2-SNAPSHOT.jar .
#docker push luiszavaleta/auth-app:local.7
docker run -p 8082:8080 --name auth-backened --network="auth-network" luiszavaleta/auth-app:local.7

