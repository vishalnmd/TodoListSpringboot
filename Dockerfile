
#FROM openjdk:17-jdk-alpine
#
#WORKDIR /app
#
#COPY target/com.springboot.login.user-0.0.1-SNAPSHOT.jar app.jar
#
#EXPOSE 8080
#
#ENTRYPOINT ["java", "-jar", "app.jar"]

FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Final stage
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/com.springboot.login.user-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]