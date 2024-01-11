FROM openjdk:21-slim

EXPOSE 80

WORKDIR /app

COPY target/*.jar ./app.jar

CMD ["java", "-jar", "app.jar"]