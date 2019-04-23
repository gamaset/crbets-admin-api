package com.gamaset.crbetadmin.schema.request;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.constraints.*;

public class SignUpRequest {
	@NotBlank
	@Size(min = 3, max = 50)
	private String name;

	@NotBlank
	@Size(max = 60)
	@Email
	private String email;

	private Set<String> role;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;

	@NotBlank
	@Size(min = 11, max = 14)
	private String taxId;

	public SignUpRequest() {
	}

	public SignUpRequest(UserRequest request, String rolename) {
		setEmail(request.getEmail());
		setName(request.getName());
		setPassword(request.getPassword());
		setTaxId(request.getTaxId());
		Set<String> roles = new HashSet<>();
		if (Objects.isNull(rolename)) {
			roles.add("CUSTOMER");
		} else {
			roles.add(rolename);
		}
		setRole(roles);
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public Set<String> getRole() {
		return this.role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}
}