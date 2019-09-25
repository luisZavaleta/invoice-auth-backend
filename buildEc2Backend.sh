./gradlew build
java -jar ./build/libs/auth-0.0.1-SNAPSHOT.jar --spring.profiles.active=ec2 --spring.data.mongodb.host=$1
