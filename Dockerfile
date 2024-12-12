# syntax=docker/dockerfile:1
FROM gradle:8.5-jdk21 AS build

COPY . /home/gradle/src
WORKDIR /home/gradle/src

RUN --mount=type=secret,id=github_token,env=GITHUB_TOKEN,required \
    gradle assemble

FROM amazoncorretto:21.0.4
EXPOSE 8080

RUN mkdir -p /usr/local/newrelic


ADD ./newrelic-java/newrelic/newrelic.jar /usr/local/newrelic/newrelic.jar
ADD ./newrelic-java/newrelic/newrelic.yml /usr/local/newrelic/newrelic.yml
COPY --from=build /home/gradle/src/build/libs/PrintScriptService-0.0.1-SNAPSHOT.jar ./PrintScriptService-0.0.1-SNAPSHOT.jar


ENTRYPOINT ["java", "-jar", "-javaagent:/usr/local/newrelic/newrelic.jar", "./PrintScriptService-0.0.1-SNAPSHOT.jar"]

