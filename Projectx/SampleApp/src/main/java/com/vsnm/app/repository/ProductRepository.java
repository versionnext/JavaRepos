package com.vsnm.app.repository;

import org.springframework.stereotype.Component;

import com.vsnm.app.model.Product;
import com.vsnm.framework.repository.BaseMongoRepository;

@Component
public interface ProductRepository extends BaseMongoRepository<Product, String> {
}
