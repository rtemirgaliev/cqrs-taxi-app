package com.epam.javacc.microservices.drivercmd.configuration;

import com.epam.javacc.microservices.drivercmd.assignment.OrderAssignmentSaga;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.commandhandling.AsynchronousCommandBus;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.CommandBusConnector;
import org.axonframework.commandhandling.distributed.CommandRouter;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.SubscribingEventProcessor;
import org.axonframework.eventhandling.saga.AnnotatedSagaManager;
import org.axonframework.eventhandling.saga.ResourceInjector;
import org.axonframework.eventhandling.saga.repository.AnnotatedSagaRepository;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.annotation.ParameterResolverFactory;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.messaging.interceptors.TransactionManagingInterceptor;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.axonframework.springcloud.commandhandling.SpringCloudCommandRouter;
import org.axonframework.springcloud.commandhandling.SpringHttpCommandBusConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestOperations;

/**
 *
 * @author Rinat Temirgaliev
 */
@Configuration
public class AxonConfiguration {


    @Bean
    public SubscribingEventProcessor createSaga(SagaStore sagaStore, SpringAMQPMessageSource ms, ResourceInjector ri,
                                                ParameterResolverFactory prf, TransactionManager tm) {
        String simpleName = OrderAssignmentSaga.class.getSimpleName();
        AnnotatedSagaRepository sagaRepository = new AnnotatedSagaRepository(OrderAssignmentSaga.class, sagaStore, ri, prf);
        AnnotatedSagaManager sagaManager = new AnnotatedSagaManager(OrderAssignmentSaga.class, sagaRepository, prf);


        SubscribingEventProcessor sep = new SubscribingEventProcessor(simpleName + "Processor", sagaManager, ms);

        sep.registerInterceptor(new TransactionManagingInterceptor(tm));

        sep.start();
        return sep;
    }



    @Autowired
    public void configure(EventHandlingConfiguration config, TransactionManager tm) {

        config.registerHandlerInterceptor("taxiExchange", configuration -> {
            return new TransactionManagingInterceptor(tm);

        });
    }


    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new JpaTransactionManager();
    }


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
//        SimpleCommandBus commandBus = new AsynchronousCommandBus();
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
