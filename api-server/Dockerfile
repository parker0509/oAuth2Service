FROM openjdk:17-jdk

WORKDIR /app
COPY build/libs/*SNAPSHOT.jar studymatching_api.jar

ENTRYPOINT ["java", "-jar", "/app/studymatching_api.jar"]