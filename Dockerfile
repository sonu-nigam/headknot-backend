# First stage: build the application
FROM amazoncorretto:21 AS builder
COPY . /app
WORKDIR /app
RUN ./gradlew clean build
RUN cd /app/build/libs
RUN ls

# Second stage: create a slim image
FROM amazoncorretto:21
LABEL org.opencontainers.image.authors="workflow.com"
ARG jar_file=build/libs/workflow-1.0-SNAPSHOT.jar
COPY --from=builder /app/${jar_file} /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

