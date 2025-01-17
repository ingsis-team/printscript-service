FROM gradle:8.5-jdk21 AS build

ARG USERNAME
ARG GIT_TOKEN

ENV GIT_TOKEN=${GIT_TOKEN}
ENV GITHUB_ACTOR ${USERNAME}



COPY  . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle assemble

FROM amazoncorretto:21-alpine
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-jar","-javaagent:/usr/local/newrelic/newrelic.jar","-Dspring.profiles.active=production","/app/spring-boot-application.jar"]