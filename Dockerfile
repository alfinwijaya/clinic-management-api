FROM maven:3.9.7-sapmachine-22 AS builder

WORKDIR /app
COPY pom.xml ./
RUN mvn -N wrapper:wrapper -Dmaven=3.9.7
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM openjdk:22-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]