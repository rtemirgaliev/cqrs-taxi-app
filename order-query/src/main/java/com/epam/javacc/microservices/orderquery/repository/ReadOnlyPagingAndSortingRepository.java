package com.epam.javacc.microservices.orderquery.repository;

import com.epam.javacc.microservices.orderquery.domain.Order;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@NoRepositoryBean
public interface ReadOnlyPagingAndSortingRepository extends PagingAndSortingRepository <Order, String> {

    @Override
    @SuppressWarnings("unchecked")
    @RestResource(exported = false)
    Order save(Order entity);

    @Override
    @RestResource(exported = false)
    void delete(String aLong);

    @Override
    @RestResource(exported = false)
    void delete(Order order);
}
