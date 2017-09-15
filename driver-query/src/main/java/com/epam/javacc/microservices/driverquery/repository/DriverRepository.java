package com.epam.javacc.microservices.driverquery.repository;

import com.epam.javacc.microservices.driverquery.domain.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, String>{
}
