FROM openjdk:17-jdk-alpine
COPY target/lciii-scaffolding-0.0.1-SNAPSHOT.jar miapi-app.jar
ENTRYPOINT ["java", "-jar", "miapi-app.jar"]