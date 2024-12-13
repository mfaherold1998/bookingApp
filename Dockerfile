FROM eclipse-temurin:17-jdk-alpine

WORKDIR /bookingApp

COPY target/booking-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

RUN mkdir -p logs

ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]