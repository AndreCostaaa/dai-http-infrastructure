FROM openjdk:21-slim

EXPOSE 5000

WORKDIR /app

COPY target/app.jar ./app.jar

CMD ["java", "-jar", "app.jar"]
