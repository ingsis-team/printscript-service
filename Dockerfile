# Base image
FROM gradle:8.5-jdk21 AS build

# Switch to root user
USER root

# Update system and install PostgreSQL client
RUN apt-get update && apt-get install -y postgresql-client

# Copy source code and .env to the container
COPY . /home/gradle/src
COPY .env /home/gradle/src/.env
WORKDIR /home/gradle/src

# Pass environment variables to Gradle build
ARG GITHUB_TOKEN
ENV GITHUB_TOKEN=${GITHUB_TOKEN}


# Install dependencies and build the application
RUN gradle assemble --no-daemon

# Create directory for New Relic
RUN mkdir -p /usr/local/newrelic

# Add New Relic agent and config
ADD ./newrelic-java/newrelic/newrelic.jar /usr/local/newrelic/newrelic.jar
ADD ./newrelic-java/newrelic/newrelic.yml /usr/local/newrelic/newrelic.yml

# Expose application port
EXPOSE 8080

# Run application
ENTRYPOINT ["java", "-jar", "-javaagent:/usr/local/newrelic/newrelic.jar", "/home/gradle/src/build/libs/printscript-service-0.0.1-SNAPSHOT.jar"]

