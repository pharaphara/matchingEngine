FROM openjdk:11
VOLUME [ "/tmp" ]
ADD matchengine.war app.war
EXPOSE 8080
ENTRYPOINT [ "sh","-c","java -war /app.war" ]
