FROM eclipse-temurin:17-jdk as builder
COPY . /app
WORKDIR /app
RUN chmod +x gradlew
RUN ./gradlew bootJar

FROM eclipse-temurin:17-jre
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
