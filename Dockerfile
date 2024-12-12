FROM openjdk:17-jdk-alpine
EXPOSE 8090
COPY target/eventsProject-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]