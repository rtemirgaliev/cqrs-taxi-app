package com.epam.javacc.microservices.drivercmd;

import org.axonframework.config.EventHandlingConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Rinat Temirgaliev
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);


    public static void main(String... args) throws UnknownHostException {

        SpringApplication app = new SpringApplication(Application.class);

        ConfigurableApplicationContext context = app.run(args);

        Environment env = context.getEnvironment();

        LOG.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\thttp://127.0.0.1:{}\n\t" +
                "External: \thttp://{}:{}\n +" +
                 "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port")
        );




//        CommandGateway commandGateway = context.getBean(CommandGateway.class);

//        commandGateway.send(new CreateDriverCommand("d1", "Speedy Driver"));
//        commandGateway.send(new ChangeDriverStatusCommand("d1", DriverStatus.EMPTY, "tr999"));
//        commandGateway.send(new CreateOrderCommand("o1", "new", "111-222", "Red Square", OrderStatus.NOT_ASSIGNED));
//        commandGateway.send(new StartOrderAssignmentCommand("a1", "o1", "d1"));
//
//        commandGateway.send(new CreateOrderCommand("o2", "new", "333-444", "White House", OrderStatus.NOT_ASSIGNED));
//        commandGateway.send(new StartOrderAssignmentCommand("a2", "o2", "d1"));


    }

}
