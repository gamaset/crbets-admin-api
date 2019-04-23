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

import com.gamaset.crbetadmin.schema.request.CustomerRequest;

@Entity
@Table(name = "cliente")
public class CustomerModel extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente")
	private Long id;

	@Column(name = "desc_nome", nullable = false)
	private String name;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "cpf", nullable = false, unique = false)
	private String taxId;

	@ManyToOne
	@JoinColumn(name = "id_usuario_agente_fk", nullable = false)
	private UserModel agent;

	public CustomerModel() {
	}
	
	public CustomerModel(CustomerRequest request, UserModel agentModel) {
		setEmail(request.getEmail());
		setName(request.getName());
		setTaxId(request.getTaxId());
		this.agent = agentModel;
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
