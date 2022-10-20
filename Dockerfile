FROM maven:3.8.6-openjdk-11 AS base
WORKDIR /app
COPY pom.xml /app/pom.xml
RUN mvn dependency:go-offline
COPY src /app/src
EXPOSE 8080
CMD [ "mvn", "spring-boot:run" ]
