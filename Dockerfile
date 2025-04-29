FROM gradle:8.5.0-jdk17 AS builder

WORKDIR /home/gradle/project

COPY --chown=gradle:gradle gradle gradle
COPY --chown=gradle:gradle gradlew build.gradle settings.gradle ./

RUN ./gradlew dependencies --no-daemon || true

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]