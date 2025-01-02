FROM openjdk:21-jdk

WORKDIR /app

COPY build/libs/project-management-system-1.0.0.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]



