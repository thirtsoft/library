FROM openjdk:8
MAINTAINER wokite.net
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]