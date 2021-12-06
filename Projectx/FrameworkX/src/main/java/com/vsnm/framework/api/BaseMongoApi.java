package com.vsnm.framework.api;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.util.StringUtils;

import com.vsnm.framework.model.BaseModel;
import com.vsnm.framework.model.BaseMongoModel;
import com.vsnm.framework.model.XPage;
import com.vsnm.framework.service.BaseMongoService;

@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
@Consumes({ MediaType.APPLICATION_JSON })
public abstract class BaseMongoApi<E extends BaseMongoModel, ID extends Serializable> {

	private BaseMongoService<E, ID> baseMongoService;

	protected BaseMongoApi(BaseMongoService<E, ID> inService) {
		this.baseMongoService = inService;
	}

	@POST
	@Path("search")
	public Response search(XPage<E> page) throws Exception {
		String errMsg = ApiUtils.validateRequest(new BaseModel(page.getFilter()
				.getClientId(), page.getFilter().getTenantId()));
		if (StringUtils.isEmpty(errMsg))
			return Response.status(Status.BAD_REQUEST).entity(errMsg).build();
		return Response.ok().entity(baseMongoService.search(page)).build();
	}

	@POST
	@Path("delete")
	public Response deleteEntityById(
			@QueryParam("id") @NotNull final ID inEntityId, BaseModel baseModel)
			throws Exception {
		String errMsg = ApiUtils.validateRequest(baseModel);
		if (StringUtils.isEmpty(errMsg))
			return Response.status(Status.BAD_REQUEST).entity(errMsg).build();
		baseMongoService.delete(inEntityId, baseModel);
		return Response.ok().build();
	}

	@POST
	@Path("get")
	public Response getEntityById(@QueryParam("id") ID inEntityId,
			BaseModel baseModel) throws Exception {
		String errMsg = ApiUtils.validateRequest(baseModel);
		if (StringUtils.isEmpty(errMsg))
			return Response.status(Status.BAD_REQUEST).entity(errMsg).build();
		return Response.ok(baseMongoService.find(inEntityId, baseModel))
				.build();
	}

	@POST
	@Path("save")
	public Response saveEntity(final E inEntity) throws Exception {
		String errMsg = ApiUtils.validateRequest(new BaseModel(inEntity
				.getClientId(), inEntity.getTenantId()));
		if (StringUtils.isEmpty(errMsg))
			return Response.status(Status.BAD_REQUEST).entity(errMsg).build();
		return Response.ok(baseMongoService.save(inEntity)).build();
	}

}
