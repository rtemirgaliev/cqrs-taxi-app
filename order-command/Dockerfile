FROM openjdk:8-jre-alpine
VOLUME /tmp
LABEL com.epam.javacc.microservices="order-command"
LABEL description="Java CC Microservices Reference Taxi Order Command-side Service Implementation"
ADD target/order-command.jar /app.jar
EXPOSE 9090
ENTRYPOINT ["java",  "-Djava.security.egd=file:/dev/urandom", "-Xmx200m", "-jar", "/app.jar"]