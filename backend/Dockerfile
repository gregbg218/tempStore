ARG ACCESS_KEY
ARG SECRET_KEY

FROM openjdk:8
VOLUME /tmp
ADD   /target/tempStore-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Xmx750m", "-jar","/app.jar"]
CMD [ $ACCESS_KEY, $SECRET_KEY ]