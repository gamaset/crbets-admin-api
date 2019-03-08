package com.gamaset.crbetadmin.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "competicao")
public class CompetitionModel {

	@Id
	@Column
	private Long id;
	@Column(name = "descricao")
	private String description;
	@Column(name = "pais_codigo")
	private String countryCode;

	public CompetitionModel() {
		// TODO Auto-generated constructor stub
	}

	public CompetitionModel(Long id, String description, String countryCode) {
		this.id = id;
		this.description = description;
		this.countryCode = countryCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
