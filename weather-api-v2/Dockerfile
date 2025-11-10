#FROM arm64v8/openjdk:21-ea-21-jdk-slim
#EXPOSE 8081
#WORKDIR /app
#COPY target/weather-api-v2-0.0.1-SNAPSHOT.jar app.jar
#CMD ["java", "-jar", "app.jar"]

FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM arm64v8/openjdk:21-ea-21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]


