package com.epam.javacc.microservices.drivercmd.configuration;

import com.rabbitmq.client.Channel;
import org.axonframework.amqp.eventhandling.DefaultAMQPMessageConverter;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.serialization.Serializer;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Bean
    public org.springframework.amqp.core.Exchange exchange() {
        return ExchangeBuilder.fanoutExchange("TaxiExchange").build();
    }


    @Autowired
    public void configure(AmqpAdmin admin) {
        admin.declareExchange(exchange());
    }


    @Bean
    public SpringAMQPMessageSource taxiExchange(Serializer serializer) {
        return new SpringAMQPMessageSource(new DefaultAMQPMessageConverter(serializer)) {

            @RabbitListener(bindings = @QueueBinding(value = @Queue,
                                                     exchange = @Exchange(value = "TaxiExchange",
                                                                          type = ExchangeTypes.FANOUT),
                                                     key = "*"))
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                super.onMessage(message, channel);
            }
        };
    }

}
