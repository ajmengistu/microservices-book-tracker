FROM adoptopenjdk/openjdk11:ubi
COPY target/*.jar app.jar
EXPOSE 9091
ENTRYPOINT [ "java", "-jar", "/app.jar" ] user-service 