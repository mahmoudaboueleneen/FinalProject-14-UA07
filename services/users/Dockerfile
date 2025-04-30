FROM openjdk:25-ea-4-jdk-oraclelinux9

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"]