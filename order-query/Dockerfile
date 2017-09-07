FROM openjdk:8-jre-alpine
VOLUME /tmp
LABEL com.epam.javacc.microservices="order-query"
LABEL description="Java CC Microservices Reference Taxi Order Query-side Service Implementation"
ADD target/order-query.jar /app.jar
EXPOSE 8001
ENTRYPOINT ["java",  "-Djava.security.egd=file:/dev/urandom", "-Xmx200m", "-jar", "/app.jar"]