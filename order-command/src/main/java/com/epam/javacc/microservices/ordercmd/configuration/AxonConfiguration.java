package com.epam.javacc.microservices.ordercmd.configuration;

import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.CommandBusConnector;
import org.axonframework.commandhandling.distributed.CommandRouter;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.messaging.interceptors.TransactionManagingInterceptor;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.axonframework.springcloud.commandhandling.SpringCloudCommandRouter;
import org.axonframework.springcloud.commandhandling.SpringHttpCommandBusConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestOperations;

/**
 *
 * @author Rinat Temirgaliev
 */
@Configuration
public class AxonConfiguration {

    /**
     * Configure distributed command bus
     */
    @Primary
    @Bean
    public DistributedCommandBus springCloudDistributedCommandBus(CommandRouter commandRouter,
                                                                  CommandBusConnector commandBusConnector) {
        DistributedCommandBus bus = new DistributedCommandBus(commandRouter, commandBusConnector);
        bus.registerDispatchInterceptor(new BeanValidationInterceptor<>());
        return bus;
    }

    @Bean
    @Qualifier("localSegment")
    public  CommandBus commandBus(TransactionManager transactionManager) {
        AsynchronousCommandBus commandBus = new AsynchronousCommandBus();
        commandBus.registerHandlerInterceptor(new TransactionManagingInterceptor(transactionManager));
        return commandBus;
    }

    /**
     * Configure connector between parts of distributed command bus
     * We choose the implementation that uses discovery service (Eureka) to discover peers
     */
    @Bean
    public CommandBusConnector springHttpCommandBusConnector(@Qualifier("localSegment") CommandBus localSegment,
                                                             RestOperations restOperations,
                                                             Serializer serializer) {
        return new SpringHttpCommandBusConnector(localSegment, restOperations, serializer);
    }

    /**
     * Configure command router for a distributed command bus.
     * We choose the implementation that uses filed with @AggregateIdentifier for routing strategy
     */
    @Bean
    public CommandRouter springCloudCommandRouter(DiscoveryClient discoveryClient) {
        return new SpringCloudCommandRouter(discoveryClient, new AnnotationRoutingStrategy());
    }


}
