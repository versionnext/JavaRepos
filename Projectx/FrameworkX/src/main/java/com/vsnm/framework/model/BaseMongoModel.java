package com.vsnm.framework.model;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.Id;

@MappedSuperclass
public abstract class BaseMongoModel extends BaseModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7353698306630869130L;

	@Id
	private String id;

	@Column(name = "ATTRIBUTES")
	private Map<String, String> attributes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

}
