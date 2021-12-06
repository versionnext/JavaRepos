package com.vsnm.framework.api;

import org.springframework.util.StringUtils;

import com.vsnm.framework.model.BaseModel;

public final class ApiUtils {

	public static String validateRequest(BaseModel baseModel) {
		String errMsg = "| ";
		if (StringUtils.isEmpty(baseModel.getClientId()))
			errMsg = "Client Id is mandatory. | ";
		if (StringUtils.isEmpty(baseModel.getTenantId()))
			errMsg = "Tenant Id is mandatory. |";
		return errMsg;
	}
}
