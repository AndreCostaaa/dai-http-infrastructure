FROM maven:3.9.6-eclipse-temurin-21 as builder


WORKDIR /app
COPY ./src ./src
RUN rm -r ./src/test
COPY ./pom.xml ./pom.xml 

RUN mvn clean package

FROM openjdk:21-slim

EXPOSE 5000

WORKDIR /app

COPY --from=builder /app/target/app.jar app.jar

CMD ["java", "-jar","app.jar"]