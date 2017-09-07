package com.epam.javacc.microservices.driverquery.repository;

import com.epam.javacc.microservices.driverquery.domain.Driver;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@NoRepositoryBean
public interface ReadOnlyPagingAndSortingRepository extends PagingAndSortingRepository <Driver, String> {

    @Override
    @SuppressWarnings("unchecked")
    @RestResource(exported = false)
    Driver save(Driver entity);

    @Override
    @RestResource(exported = false)
    void delete(String aLong);

    @Override
    @RestResource(exported = false)
    void delete(Driver order);
}
