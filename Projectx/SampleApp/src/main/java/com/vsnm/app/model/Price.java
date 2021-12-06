package com.vsnm.app.model;

import java.math.BigDecimal;

public class Price {

	private String currency;
	private BigDecimal price;

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
