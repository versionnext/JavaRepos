package com.vsnm.app.api;

import io.swagger.annotations.Api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.vsnm.app.model.Currency;
import com.vsnm.app.service.CurrencyService;
import com.vsnm.framework.api.BaseMongoApi;
import com.vsnm.framework.model.BaseModel;

@Path("/currency")
@Api(value = "Currency")
@Component
public class CurrencyApi extends BaseMongoApi<Currency, String> {

	private CurrencyService currencyService;

	protected CurrencyApi(CurrencyService inService) {
		super(inService);
		this.currencyService = inService;
	}

	@POST
	@Path("getName")
	public Response getName(@QueryParam("name") String name,
			BaseModel baseModel) throws Exception {
		return Response.ok(currencyService.findByName(name, baseModel)).build();
	}
}
