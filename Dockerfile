FROM amazoncorretto:17-alpine-jdk
COPY target/*.jar sbtech-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/sbtech-0.0.1-SNAPSHOT.jar"]