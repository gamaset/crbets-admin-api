package com.gamaset.crbetadmin.schema.response;

import java.util.List;
import java.util.stream.Collectors;

import com.gamaset.crbetadmin.repository.entity.AgentModel;
import com.gamaset.crbetadmin.repository.entity.WalletBalanceModel;
import com.gamaset.crbetadmin.schema.UserSchema;
import com.gamaset.crbetadmin.schema.WalletBalanceSchema;

public class AgentResponse {

	private Long id;
	private UserSchema user;
	private UserSchema manager;
	private List<WalletBalanceSchema> walletBalance;

	public AgentResponse() {
	}

	public AgentResponse(AgentModel agent, List<WalletBalanceModel> balances) {
		setId(agent.getId());
		setUser(new UserSchema(agent.getUser()));
		setManager(new UserSchema(agent.getManager()));
		if (!balances.isEmpty()) {
			walletBalance = balances.stream().map(b -> new WalletBalanceSchema(b)).collect(Collectors.toList());
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserSchema getUser() {
		return user;
	}

	public void setUser(UserSchema user) {
		this.user = user;
	}

	public UserSchema getManager() {
		return manager;
	}

	public void setManager(UserSchema manager) {
		this.manager = manager;
	}

	public List<WalletBalanceSchema> getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(List<WalletBalanceSchema> walletBalance) {
		this.walletBalance = walletBalance;
	}

}
