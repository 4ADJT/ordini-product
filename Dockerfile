FROM gradle:jdk21-corretto AS builder

WORKDIR /app

COPY gradlew build.gradle.kts settings.gradle.kts /app/
COPY gradle /app/gradle

RUN ./gradlew build -x test --no-daemon || true

COPY . .

RUN ./gradlew build -x test --no-daemon

FROM amazoncorretto:21 AS runner

ENV SPRING_PROFILES_ACTIVE=prod

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
