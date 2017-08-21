package com.epam.javacc.microservices.ordercmd.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.fanoutExchange("TaxiOrderExchange").build();
    }


    @Autowired
    public void configure(AmqpAdmin admin) {
        admin.declareExchange(exchange());
    }
}
