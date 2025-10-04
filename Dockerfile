FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/insightpulse-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "--enable-preview", "-jar", "app.jar"]