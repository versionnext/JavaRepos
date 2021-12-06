package com.vsnm;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.vsnm.app.api.CustomerApi;
import com.vsnm.app.model.Customer;
import com.vsnm.framework.model.BaseModel;
import com.vsnm.framework.model.XPage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerTest {
	
	@Autowired
	CustomerApi customerApi;
		
	@SuppressWarnings("unchecked")
	@Test
	public void TestApi() throws Exception {
		final String clientId="DEF";
		final String tenantId="DEF";
		Customer customer = new Customer();
		String name = "Name" + new Date().getTime();
		String email = "Email" + new Date().getTime();
		customer.setName(name);
		customer.setClientId(clientId);
		customer.setTenantId(tenantId);
		BaseModel baseModel = new BaseModel(clientId, tenantId);
		Response resp = customerApi.saveEntity(customer);
		Assert.isTrue(200 == resp.getStatus(), " saveEntity Reponse not OK");
		customer = (Customer) resp.getEntity();
		customer.setEmail(email);
		customer.setUpdatedBy("VIPIN");
		resp = customerApi.saveEntity(customer);
		List<Customer> customerList = customerApi.findByName(name);
		Assert.isTrue(email.equals(customerList.get(0).getEmail()),
				"Email not matched");
		resp = customerApi.getEntityById(customer.getId(),baseModel);
		Assert.isTrue(200 == resp.getStatus(), " getEntityById Reponse not OK");
		customer = (Customer) resp.getEntity();
		Assert.isTrue(name.equals(customer.getName()), "Name not matched");
		XPage<Customer> page = new XPage<Customer>();
		page.setFilter(customer);
		resp = customerApi.search(page);
		page = (XPage<Customer>) resp.getEntity();
		Assert.isTrue(email.equals(Lists.newArrayList(page.getContent()).get(0).getEmail()), "Email not matched");	
		resp = customerApi.deleteEntityById(customer.getId(),baseModel);
		Assert.isTrue(200 == resp.getStatus(),
				" deleteEntityById Reponse not OK");
		resp = customerApi.getEntityById(customer.getId(),baseModel);
		resp.getEntity();
		Assert.isNull(resp.getEntity(), "Reponse should be null");


	}
}