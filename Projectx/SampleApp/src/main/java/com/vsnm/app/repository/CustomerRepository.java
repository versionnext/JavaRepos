package com.vsnm.app.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.vsnm.app.model.Customer;
import com.vsnm.framework.repository.BaseJpaRepository;

@Component
public interface CustomerRepository extends BaseJpaRepository<Customer, Long> {
	List<Customer> findByName(String name);

}
