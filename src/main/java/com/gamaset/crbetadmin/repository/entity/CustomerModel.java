package com.gamaset.crbetadmin.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "cliente")
public class CustomerModel extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_usuario_fk", nullable = false)
	private UserModel user;

	@ManyToOne
	@JoinColumn(name = "id_usuario_agente_fk", nullable = false)
	private UserModel agent;

	public CustomerModel() {
	}
	
	public CustomerModel(UserModel userCreated, UserModel agentModel) {
		this.user = userCreated;
		this.agent = agentModel;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserModel getAgent() {
		return agent;
	}

	public void setAgent(UserModel agent) {
		this.agent = agent;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
