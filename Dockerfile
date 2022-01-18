FROM openjdk:alpine-slim
VOLUME [ "/tmp" ]
ADD matchingEngine-matchingEngine.war app.war
EXPOSE 8080
ENTRYPOINT [ "sh","-c","java -war /app.war" ]
