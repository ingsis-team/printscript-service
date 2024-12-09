# Base image
FROM gradle:8.5-jdk21 AS build

COPY . /home/gradle/src
WORKDIR /home/gradle/src


ARG TOKEN
ENV TOKEN=$TOKEN

RUN gradle assemble


RUN unset TOKEN

FROM amazoncorretto:21.0.4
EXPOSE 8080

RUN mkdir -p /usr/local/newrelic


ADD ./newrelic-java/newrelic/newrelic.jar /usr/local/newrelic/newrelic.jar
ADD ./newrelic-java/newrelic/newrelic.yml /usr/local/newrelic/newrelic.yml
COPY --from=build /home/gradle/src/build/libs/PrintscriptService-0.0.1-SNAPSHOT.jar ./PrintscriptService-0.0.1-SNAPSHOT.jar


ENTRYPOINT ["java", "-jar", "-javaagent:/usr/local/newrelic/newrelic.jar", "./PrintscriptService-0.0.1-SNAPSHOT.jar"]

