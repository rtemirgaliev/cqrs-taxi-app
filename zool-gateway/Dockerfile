FROM openjdk:8-jre-alpine
VOLUME /tmp
LABEL com.epam.javacc.microservices="zool"
LABEL description="Java CC Microservices Reference Taxi Gateway Service"
ADD target/zool-gateway.jar /app.jar
EXPOSE 7777
ENTRYPOINT ["java",  "-Djava.security.egd=file:/dev/urandom", "-Xmx200m", "-jar", "/app.jar"]