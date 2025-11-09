FROM arm64v8/openjdk:21-ea-21-jdk-slim

EXPOSE 8081

WORKDIR /app

COPY target/weather-api-v1-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
