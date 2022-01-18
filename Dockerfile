From tomcat:8.5-jdk11-corretto
RUN rm -rf /usr/local/tomcat/webapps/*
COPY matchingEngine-matchingEngine.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh","run"]
