FROM openjdk:11-jdk-slim
COPY ./build/libs/metrics-api-1.0-SNAPSHOT.jar /app/metrics-api.jar
ENTRYPOINT ["java"]
CMD ["-jar", "/app/metrics-api.jar"]
EXPOSE 8080
HEALTHCHECK CMD curl -f http://localhost:8080/actuator/health || exit 1