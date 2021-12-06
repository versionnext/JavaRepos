package com.vsnm.app.api;

import io.swagger.annotations.Api;

import javax.ws.rs.Path;

import org.springframework.stereotype.Component;

import com.vsnm.app.model.Product;
import com.vsnm.app.service.ProductService;
import com.vsnm.framework.api.BaseMongoApi;

@Path("/product")
@Api(value = "Product")
@Component
public class ProductApi extends BaseMongoApi<Product, String> {

	protected ProductApi(ProductService inService) {
		super(inService);
	}

}
