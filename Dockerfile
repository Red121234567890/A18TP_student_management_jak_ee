FROM tomcat:10-jdk17
LABEL maintainer="your-email@example.com"

# Remove default Tomcat applications
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy our WAR file to webapps directory
COPY target/students.war /usr/local/tomcat/webapps/ROOT.war

# Expose the default Tomcat port
EXPOSE 8080

CMD ["catalina.sh", "run"]