package com.vsnm.app.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.vsnm.framework.model.BaseMongoModel;

@Document(collection = "product")
public class Product extends BaseMongoModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2158287848786597674L;
	private String code;
	private String name;
	private String description;

	private List<Price> prices;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

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
