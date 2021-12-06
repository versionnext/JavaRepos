package com.vsnm.app.listeners;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.vsnm.app.model.Customer;

public class Listener {
	/**
	 * automatic property set before any database persistence
	 */
	@PreUpdate
	@PrePersist
	public void setLastUpdate(Customer o) {
		o.setUpdatedOn(new Date());
	}
}
