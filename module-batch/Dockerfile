FROM openjdk:17-jdk

WORKDIR /app

ARG JAR_FILE=./build/libs/module-batch-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} washpedia-batch.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "washpedia-batch.jar"]