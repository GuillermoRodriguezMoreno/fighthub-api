FROM maven:3.9.2-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml .
COPY src ./src
RUN mvn -B package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]