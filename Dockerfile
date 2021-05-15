FROM qaseekermonsterindia.azurecr.io/alpine-oraclejdk8:v1

MAINTAINER Nidhi Arora 

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE
ARG PROFILE

RUN echo "Copying $JAR_FILE to current directory"

COPY ${JAR_FILE} vulture.jar

#ADD run.sh /usr/local/bin/run.sh
#RUN chmod +x /usr/local/bin/run.sh

#ENTRYPOINT ["sh","/usr/local/bin/run.sh"]

# add new relic file
ADD newrelic /newrelic

ENTRYPOINT ["java","-Dspring.profiles.active=production,spring","-XX:+UseG1GC","-XX:+UseStringDeduplication","-Xms2g","-Xmx2g","-jar","/vulture.jar"]
