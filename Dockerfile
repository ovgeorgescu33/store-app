FROM openjdk:14-jdk-alpine
COPY store-service/target/store-service-0.0.1-SNAPSHOT.jar store-service.jar
ENTRYPOINT ["java","-jar","/store-service.jar"]