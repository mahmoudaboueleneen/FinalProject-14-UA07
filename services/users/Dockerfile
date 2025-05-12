FROM curlimages/curl:8.12.1 AS download
ARG OTEL_AGENT_VERSION="2.14.0"
RUN curl --silent --fail --insecure -L "https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v${OTEL_AGENT_VERSION}/opentelemetry-javaagent.jar" \
    -o "$HOME/opentelemetry-javaagent.jar"

FROM openjdk:23-jdk

WORKDIR /app

COPY target/*.jar app.jar

COPY --from=download /home/curl_user/opentelemetry-javaagent.jar /opentelemetry-javaagent.jar

EXPOSE 8085

ENTRYPOINT ["java", \
   "-javaagent:/opentelemetry-javaagent.jar", \
  "-jar", "app.jar"]
