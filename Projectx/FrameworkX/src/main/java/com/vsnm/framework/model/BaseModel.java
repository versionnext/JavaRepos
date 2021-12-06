package com.vsnm.framework.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public class BaseModel {

	@JsonIgnore
	@Column(name = "CREATED_BY")
	private String createdBy;

	@JsonIgnore
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@JsonIgnore
	@Column(name = "CREATED_ON")
	private Date createdOn;

	@JsonIgnore
	@Column(name = "UPDATED_ON")
	private Date updatedOn;

	@JsonIgnore
	@Column(name = "DELETED")
	private Boolean deleted;

	@Column(name = "TENANT_ID")
	@NotNull(message = "Tenant Id is mandatory")
	private String tenantId;

	@Column(name = "CLIENT_ID")
	@NotNull(message = "Client Id is mandatory")
	private String clientId;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public BaseModel(String clientId,String tenantId) {
		this.clientId = clientId;
		this.tenantId = tenantId;
	}
	
	public BaseModel(){
	}

}
