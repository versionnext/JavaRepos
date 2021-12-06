package com.vsnm.app.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.vsnm.framework.model.BaseMongoModel;

@Document(collection = "currency")
public class Currency extends BaseMongoModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2158287848786597674L;
	private String code;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
