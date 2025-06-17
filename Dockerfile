FROM maven:3.9.2-eclipse-temurin-17 AS builder
WORKDIR /app

COPY fighthub-api/pom.xml .
COPY fighthub-api/src ./src
RUN mvn -B package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8080

ENTRYPOINT ["sh","-c","java -Dserver.address=0.0.0.0 -Dserver.port=$PORT -jar app.jar"]
