package com.gamaset.crbetadmin.repository.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mercado")
public class MarketModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "id_mercado", nullable = false)
	private String marketId;

	@Column(name = "descricao", nullable = false)
	private String marketName;

	@JoinColumn(name = "id_mercado_selecao_fk")
	@ManyToOne(cascade = CascadeType.ALL)
	private MarketSelectionModel price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String id) {
		this.marketId = id;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String description) {
		this.marketName = description;
	}

	public MarketSelectionModel getPrice() {
		return price;
	}

	public void setPrice(MarketSelectionModel selection) {
		this.price = selection;
	}

}
