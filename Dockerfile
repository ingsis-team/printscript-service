# Base image
FROM gradle:8.5-jdk21 AS build

COPY . /home/gradle/src
WORKDIR /home/gradle/src

ARG USERNAME_PRINTSCRIPT
ARG GIT_TOKEN
ENV USERNAME_PRINTSCRIPT=$USERNAME_PRINTSCRIPT
ENV GIT_TOKEN=$GIT_TOKEN

RUN gradle assemble
RUN unset USERNAME_PRINTSCRIPT GIT_TOKEN


FROM amazoncorretto:21.0.4
EXPOSE 8080

RUN mkdir -p /usr/local/newrelic


ADD ./newrelic-java/newrelic/newrelic.jar /usr/local/newrelic/newrelic.jar
ADD ./newrelic-java/newrelic/newrelic.yml /usr/local/newrelic/newrelic.yml



ENTRYPOINT ["java", "-jar", "-javaagent:/usr/local/newrelic/newrelic.jar", "/home/gradle/src/build/libs/printscript-service-0.0.1-SNAPSHOT.jar"]

