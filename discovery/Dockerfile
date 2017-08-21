FROM openjdk:8-jre-alpine
VOLUME /tmp
LABEL com.epam.javacc.microservices="discovery"
LABEL description="Java CC Microservices Reference Taxi Discovery Service"
ADD target/discovery.jar /app.jar
EXPOSE 8761
ENTRYPOINT ["java",  "-Djava.security.egd=file:/dev/urandom", "-Xmx200m", "-jar", "/app.jar"]