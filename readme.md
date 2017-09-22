###About


This text is for previous version of application. Will be redone later.


This is a simple prototype of an application based on CQRS and Event Sourcing architecture.


The application consists of the following services:
- Order-command service. Command part of CQRS architecture. It processes write requests.
- Order-query service. Query part of CQRS architecture. It processes read requests.
- RabbitMQ. Message broker that is used for delivering events from order-command to order-query service.
- Discovery service Eureka.

Each of the business services has its own database. Currently it is an embedded HSQL database. 
It will probably be replaced later with MongoDB or something else.

RabbitMQ will probably be replaced later with Kafka.

Application uses Axon Framework (http://www.axonframework.org) that helps
to construct CQRS infrastructure.

See comments in the source code about how each part works.
See readme-about-axon.md file with the description of Axon Framework.


###Testing the application

Go to the project directory. Build the project with:
```
# mvn clean install
```
---
Start infrastructure services with docker-compose:
```
# docker-compose up -d discovery
# docker-compose up -d rabbitmq
```
---
Start several instances of each business service. Preferably in different terminal windows to see logs:
```
# docker-compose up --scale order-command=2 order-command
# docker-compose up --scale order-query=2 order-query
```
---
Get known of ip addresses of started containers:
```
# docker network inspect cqrstaxiapp_default
```
---
Create new orders by making POST request to one of the order-command services.
```
# http POST <order-command-ip>:9090/order  businessKey=open phone=444-555 address=Nagatinskaya status=NEW
```
`http` utility here is an alternative to `curl`, you can install it with linux package "httpie", or use `curl`

---
See logs from order-command services like:
```
... CreateOrderRequest request received
... CreateOrderCommand is sending to command gateway
... Command: 'CreateOrderCommand' received
... Queuing up a new OrderCreatedEvent for order '1ccc11ce-218e-462a-8f4b-f0e7708170d2'
... Applied: 'OrderCreatedEvent' [1ccc11ce-218e-462a-8f4b-f0e7708170d2]
```
All of these log events may come from the instance of order-command service that received your request.
But, because we have a distributed version of command bus, the command may be routed form one instance to another.
In that case the last several lines of log may belong to the different instance of order-command service.
The routing decision in our case is based on order ID.

---
See logs from order-query services like:
```
... OrderCreatedEvent: [1ccc11ce-218e-462a-8f4b-f0e7708170d2]
... OrderCreatedEvent: [1ccc11ce-218e-462a-8f4b-f0e7708170d2]
```
Each instance of the order-query should show in log that it has received the event.
This is because each instance has its own copy of the database. 

---
Query any instance of order-query service for the list of all orders:
```
# http <order-query-ip>:9191/orders
```

---
To update the existing order use:
```
# http PUT <order-command-ip>:9090/order orderId=1ccc11ce-218e-462a-8f4b-f0e7708170d2 businessKey=open phone=999-999 address=redsquare status=NEW
```



