package com.epam.javacc.microservices.orderquery.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrderRepository extends ReadOnlyPagingAndSortingRepository {

}
