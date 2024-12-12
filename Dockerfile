FROM openjdk:17-jdk-alpine
EXPOSE 8090
ADD target/eventsProject-1.0.0.jar
ENTRYPOINT ["java","-jar","/eventsProject-1.0.0.jar"]