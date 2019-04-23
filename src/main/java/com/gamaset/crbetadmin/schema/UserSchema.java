package com.gamaset.crbetadmin.schema;

import com.gamaset.crbetadmin.repository.entity.UserModel;

public class UserSchema {

	private Long id;
	private String name;
	private String email;
	private String taxId;

	public UserSchema() {
	}

	public UserSchema(UserModel user) {
		setId(user.getId());
		setName(user.getName());
		setEmail(user.getEmail());
		setTaxId(user.getTaxId());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

}
