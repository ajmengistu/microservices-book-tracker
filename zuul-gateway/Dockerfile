FROM adoptopenjdk/openjdk11:ubi
COPY target/*.jar app.jar
EXPOSE 8081 
ENTRYPOINT [ "java", "-jar", "/app.jar" ] zuul-gateway