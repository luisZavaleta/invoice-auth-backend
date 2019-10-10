rm ./build/libs/auth-0.0.2-SNAPSHOT.jar || true
gradle clean
gradle build --no-build-cache
java -jar ./build/libs/auth-0.0.2-SNAPSHOT.jar --spring.profiles.active=local
