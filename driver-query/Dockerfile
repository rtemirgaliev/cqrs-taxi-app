FROM openjdk:8-jre-alpine
VOLUME /tmp
LABEL com.epam.javacc.microservices="driver-query"
LABEL description="Java CC Microservices Reference Taxi Driver Query-side Service Implementation"
ADD target/driver-query.jar /app.jar
EXPOSE 9001
ENTRYPOINT ["java",  "-Djava.security.egd=file:/dev/urandom", "-Xmx200m", "-jar", "/app.jar"]