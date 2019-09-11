FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY parking-1.0.jar app.jar
EXPOSE 7788
CMD ["java", "-jar",  "-Dserver.port=7788", "-Dspring.profiles.active=prod" , "app.jar"]