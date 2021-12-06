package com.vsnm.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.vsnm.app.listeners.Listener;
import com.vsnm.framework.model.BaseJpaModel;

@Entity
@Table(name = "CUSTOMER")
@Where(clause = "deleted = false")
@EntityListeners(value = Listener.class)
public class Customer extends BaseJpaModel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1277142255436932442L;

	@Column(name = "NAME")
	private String name;
	@Column(name = "EMAIL")
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
