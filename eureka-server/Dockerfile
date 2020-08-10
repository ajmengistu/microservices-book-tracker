FROM adoptopenjdk/openjdk11:ubi
COPY target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT [ "java", "-jar", "/app.jar" ] eureka-server