FROM openjdk:8-jdk-alpine

MAINTAINER Nidhi Arora 

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE


RUN echo "Copying $JAR_FILE to current directory"

COPY ${JAR_FILE} weather-forecast.jar

#ADD run.sh /usr/local/bin/run.sh
#RUN chmod +x /usr/local/bin/run.sh

#ENTRYPOINT ["sh","/usr/local/bin/run.sh"]


ENTRYPOINT ["java","-Dspring.profiles.active=production,spring","-XX:+UseG1GC","-XX:+UseStringDeduplication","-Xms2g","-Xmx2g","-jar","/weather-forecast.jar"]
