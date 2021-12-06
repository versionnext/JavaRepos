package com.vsnm.app.service;

import org.springframework.stereotype.Service;

import com.vsnm.app.model.Product;
import com.vsnm.app.repository.ProductRepository;
import com.vsnm.framework.service.BaseMongoService;

@Service
public class ProductService extends BaseMongoService<Product, String> {

	protected ProductService(ProductRepository inRepository) {
		super(inRepository);
	}

}
