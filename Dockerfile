FROM openjdk:15
VOLUME tmp
ADD target/<build-jar-name>.jar <docker-jar-name>.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]