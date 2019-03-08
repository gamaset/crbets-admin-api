package com.gamaset.crbetadmin.repository.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password", "roles"})
@Entity
@Table(name = "usuario", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
		@UniqueConstraint(columnNames = { "email" }), @UniqueConstraint(columnNames = { "cpf" }) })
public class UserModel extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;

	@Column(name = "desc_nome", nullable = false)
	private String name;

	@Column(name = "email", nullable = false, unique = false)
	private String email;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "cpf", nullable = false, unique = false)
	private String taxId;

	@Column(name = "senha", nullable = false)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_regras", joinColumns = @JoinColumn(name = "id_usuario", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_regra", nullable = false))
	private Set<Role> roles = new HashSet<>();

	public UserModel() {
	}

	public UserModel(String name, String username, String email, String password, String taxId) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.taxId = taxId;
    }
	
	public UserModel(Long userId) {
		this.id = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String nickname) {
		this.username = nickname;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}
}
