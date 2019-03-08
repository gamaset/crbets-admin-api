package com.gamaset.crbetadmin.schema;

import com.gamaset.crbetadmin.repository.entity.WalletModel;
import com.gamaset.crbetadmin.repository.entity.WalletStatusEnum;

public class WalletSchema {

	private Long id;
	private WalletStatusEnum status;
	private UserSchema agent;

	public WalletSchema() {
	}

	public WalletSchema(WalletModel wallet) {
		setId(wallet.getId());
		setStatus(wallet.getStatus());
		setAgent(new UserSchema(wallet.getAgent().getUser()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserSchema getAgent() {
		return agent;
	}

	public void setAgent(UserSchema agent) {
		this.agent = agent;
	}

	public WalletStatusEnum getStatus() {
		return status;
	}

	public void setStatus(WalletStatusEnum status) {
		this.status = status;
	}

}
