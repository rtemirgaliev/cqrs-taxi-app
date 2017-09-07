package com.epam.javacc.microservices.driverquery.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "driver", path = "driver")
public interface DriverRepository extends ReadOnlyPagingAndSortingRepository{
}
