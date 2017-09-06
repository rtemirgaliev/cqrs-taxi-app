FROM openjdk:8-jre-alpine
VOLUME /tmp
LABEL com.epam.javacc.microservices="driver-command"
LABEL description="Java CC Microservices Reference Taxi Driver Command-side Service Implementation"
ADD target/driver-command.jar /app.jar
EXPOSE 9292
ENTRYPOINT ["java",  "-Djava.security.egd=file:/dev/urandom", "-Xmx200m", "-jar", "/app.jar"]