package com.epam.javacc.microservices.orderquery.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "order", path = "order")
public interface OrderRepository extends ReadOnlyPagingAndSortingRepository {

}
