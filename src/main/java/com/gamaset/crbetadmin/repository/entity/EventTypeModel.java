package com.gamaset.crbetadmin.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_evento")
public class EventTypeModel extends Auditable {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "descricao")
	private String description;

	@Column(name = "ativo")
	private boolean active;

	public EventTypeModel() {
	}

	public EventTypeModel(Long id, String description, boolean active) {
		this.id = id;
		this.description = description;
		this.active = active;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
