FROM maven:3.8.2-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src

RUN mvn clean package
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE ${MY_PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]