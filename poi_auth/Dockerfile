FROM openjdk:8-jre-alpine

WORKDIR /home/app

COPY target/poi_auth-1.0.jar .

ENTRYPOINT java -Dspring.profiles.active=docker -jar *.jar
