FROM openjdk:22-jdk as java

WORKDIR /app

COPY . /app

RUN ./mvnw clean package -DskipTests

WORKDIR /app/target

ENTRYPOINT ["java","-jar","clinic-0.0.1-SNAPSHOT.jar"]