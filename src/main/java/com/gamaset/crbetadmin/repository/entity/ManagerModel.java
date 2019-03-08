package com.gamaset.crbetadmin.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "gerente")
public class ManagerModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_gerente")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_usuario_fk", nullable = false)
	private UserModel user;
	
	public ManagerModel() {
	}

	public ManagerModel(Long managerId) {
		this.id = managerId;
	}

	public ManagerModel(UserModel userCreated) {
		this.user = userCreated;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

}
