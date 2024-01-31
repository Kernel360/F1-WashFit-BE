FROM openjdk:17-jdk

WORKDIR /app

ARG JAR_FILE=./build/libs/module-api-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} washpedia-api.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "washpedia-api.jar"]