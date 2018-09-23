FROM openjdk:8-jre-alpine

WORKDIR /home/app

COPY target/poi_config-1.0.jar .

ENTRYPOINT java -Dserver.port=8080 -jar *.jar
