package com.vsnm.app.api;

import io.swagger.annotations.Api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.stereotype.Component;

import com.vsnm.app.model.Customer;
import com.vsnm.app.service.CustomerService;
import com.vsnm.framework.api.BaseJpaApi;

@Path("/customer")
@Api(value = "Customer")
@Component
public class CustomerApi extends BaseJpaApi<Customer, Long> {

	private CustomerService customerService;

	protected CustomerApi(CustomerService inService) {
		super(inService);
		this.customerService = inService;
	}

	@Path("findByName")
	@GET
	public List<Customer> findByName(@QueryParam(value = "name") String name)
			throws Exception {
		return customerService.findByName(name);
	}

}
