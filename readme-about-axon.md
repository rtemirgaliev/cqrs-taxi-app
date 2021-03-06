## About AXON

### Commands 

Commands are messages that are sent to a system with an intent of doing something. They are sent to a Command Gateway, and then a Command Bus dispatches them to concrete Command Handlers. There can be some validation, or security check done in some Interceptors. The Command should be executed in some Unit of Work, which can be implemented over database transaction. Additionally Command processing may be distributed across many nodes.

Axon provides interfaces, implementation and annotations for each one of those concepts. You just need to write your Command classes, and simple Handler classes with one annotation and that's it.

There are some default Interceptors already written - like Interceptor for JSR 303 Bean Validation. If you need, you can easily write your Interceptor by implementing one simple interface.

### Events

As stated before, Commands are messages with intent of doing something. On the other hand, after this something is done, another messages can be produced as a result - Events. They represent a fact.

In Axon, you just need to write your own Event classes. All the infrastructure that is responsible for handling them is there for you. In previous version (1.3) you had to extend base Event class - in current version (2.0) you don't have to do it anymore. Because of that, you can use your Events to integrate with other, non-Axon-based systems easily.

### Domain

You should model your domain very carefully. This is the place, where the essential complexity lays. There are no frameworks that can help you with that. You should spend most of your time in that place.

Thanks to Axon, you can actually do it that way. All the infrastructure code is there for you. The one thing that Axon gives you to help you struggling with the domain modeling is base Aggregate Root class, which you can extend and get access to some useful methods.

In the end, if you are using Event Sourcing, you can use Abstract Annotated Aggregate Root and use annotations on your private methods, that are applying events and changing the state of the Aggregate. Axon will additionally dispatch Events to annotated Entities in the Aggregate.

You should not change the state of the Aggregate in methods that are not reacting on Events. This is normal in Event Sourcing. Axon guards you not to violate this principle, so an inexperienced person won't break anything.

### Repositories

Aggregates are stored in Repositories. You should be able to load Aggregate by id, and save it back. It doesn't matter if you are using SQL database, flat files, or other noSQL solution. The persistance technique should be separated from the concept.

Axon gives you this separation. You can choose to use classic JPA-based Repository, or Event Sourced Repository. It additionally adds possibility to use caching for performance tuning.

### Event Stores

If you choose to use Event Sourcing, you need to store effectively your Events in some Event Store.

Axon gives you an interface for Repository, and a couple of implementations. The simplest implementation is based on flat files, but it is not that powerfull as others. You can also use JPA-based Event Store, which will create only two tables - for Event entries and for Aggregate Snapshot entries. For those two solutions, you will need to serialize your Events. Axon by default uses XStream, but you can choose any other. The third Event Store is implemented over MongoDB. If you need, you can easily implement any other Event Store on your own.

When your Aggregates live for a very long time, they can have a quite long history. For performance reasons you can use Aggregate Snapshotting to shorten the time of Aggregate loading.

Your Events definition may change over the time, so those Event Stores by default give you a possibility to easily write Event Upcasters. It also gives you a support for some advanced conflict resolution when it happens. 

### Event Bus 

In CQRS, after Events are generated, they need to be processed to update the query database. They are dispatched to Event Handlers. Those Event Handlers can be located on the same machine, or distributed in a cluster.

In Axon you just need to annatote methods of components that are supposed to listen for Events. You can also choose if you want to process Events synchronously, or asynchronously.

If some error occurs, you can define how to react. Axon can rollback transaction if you are using any, or it can reschedule the Event and process it again after some time for you. If you need any other error handling procedure, you can write it on your own by implementing simple interface. 

If you have distributed environment, Axon gives you support for Spring AMQP.

Sometimes you need to replay historical Events, and Axon also gives you the support for doing that.

### Sagas

Sometimes your transactions need to live longer. You cannot always finish all the work you need in a single ACID transaction. That is where Sagas come into play. On the other hand, you can use Sagas as a Workflow, or State Machine.

In Axon, Saga is a special Event Handler that handles the long business transaction. You can use Abstract Annotated Saga as a base class for your Sagas and then just use annotations on your methods that handle Events.

If you need to take care about time, or deadlines, Axon provides Sagas with Schedulers that can handle time management. You can use Simple Scheduler that uses pure Java implementation, or Quartz Scheduler that is much more powerfull.

### Testing 

You should test the code that you write, right? You can do it in a normal way, with mocks, or something like this, but if you are using Event Sourcing, you have another possibility. You have a history of Events, then you execute some Command, and a bunch of Events is generated as a result. You can test exactly that way with Axon.

Axon gives you a Given When Then Fixture. As Given you specify historical set of Events, as When you specify a Command that is tested, and as a Then you specify full set of Events that are supposed to be generated. Everything is wired for you, and you just need to define test scenario with those Events and Command.

Of course, you should also test your Sagas. There is a special Annotated Saga Test Fixture for that. As Given you specify a set of historical Event, as When you specify a single Event, and as Then you specify a desired behavior, or state change. If you need, you can also mock the time for testing time-related Sagas.

### Spring support 

Those days, each mature framework in Java world should have some sort of Spring support. Each of Axon's components can be configured as a Spring bean. Axon provides also a namespace shortcut for almost everything it has, so the configuration is as short as it has to be.
