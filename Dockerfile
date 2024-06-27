FROM 8.8.0-jdk21 as build
COPY --chown-gradle:gradle . /home/gradle/src/
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:21
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/com.benverble.ktor-sample
ENTRYPOINT ["java", "/app/com.benverble.ktor-sample.jar"]