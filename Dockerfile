FROM eclipse-temurin:25-jdk-jammy AS builder

WORKDIR /workspace

COPY gradlew gradlew.bat settings.gradle build.gradle ./
COPY gradle ./gradle
COPY app ./app
COPY core ./core
COPY infra ./infra
COPY common ./common

RUN chmod +x ./gradlew && ./gradlew :app:bootJar --no-daemon

FROM eclipse-temurin:25-jdk-jammy AS runtime

WORKDIR /app
COPY --from=builder /workspace/app/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "/app/app.jar"]
