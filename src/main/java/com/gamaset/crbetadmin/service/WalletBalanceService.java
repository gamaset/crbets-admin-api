package com.gamaset.crbetadmin.service;

import static com.gamaset.crbetadmin.infra.log.LogEvent.create;
import static com.gamaset.crbetadmin.infra.utils.CalculatorUtils.getDifference;
import static com.gamaset.crbetadmin.infra.utils.CalculatorUtils.getSum;
import static com.gamaset.crbetadmin.repository.entity.BetStatusEnum.REGISTERING;
import static com.gamaset.crbetadmin.repository.entity.BetStatusEnum.WON;
import static com.gamaset.crbetadmin.repository.entity.WalletStatusEnum.OPEN;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gamaset.crbetadmin.infra.configuration.security.UserPrinciple;
import com.gamaset.crbetadmin.infra.exception.BusinessException;
import com.gamaset.crbetadmin.infra.exception.NotFoundException;
import com.gamaset.crbetadmin.infra.log.LogEvent;
import com.gamaset.crbetadmin.infra.utils.UserProfileUtils;
import com.gamaset.crbetadmin.repository.WalletBalanceHistoryRepository;
import com.gamaset.crbetadmin.repository.WalletBalanceRepository;
import com.gamaset.crbetadmin.repository.entity.BetModel;
import com.gamaset.crbetadmin.repository.entity.BetStatusEnum;
import com.gamaset.crbetadmin.repository.entity.WalletBalanceHistoryModel;
import com.gamaset.crbetadmin.repository.entity.WalletBalanceModel;
import com.gamaset.crbetadmin.repository.entity.WalletStatusEnum;
import com.gamaset.crbetadmin.schema.WalletBalanceSchema;

@Service
public class WalletBalanceService {
	
	private static final Logger LOG_ACTION = LogEvent.logger("ACTION");
	private static final Logger LOG_ERROR = LogEvent.logger("ERROR");

	@Autowired
	private WalletBalanceTransactionService walletBalanceTransactionService;
	@Autowired
	private WalletBalanceRepository walletBalanceRepository;
	@Autowired
	private WalletBalanceHistoryRepository walletBalanceHistoryRepository;

	@Transactional
	public void updateByBetStaus(Long agentUserID, BetStatusEnum betStaus, BetModel betModel) {
		LOG_ACTION.info(create("Atualizando Saldo da Carteira").add("agentUserID", agentUserID).build());
		
		List<WalletBalanceModel> walletBalances = walletBalanceRepository.findByWalletAgentUserIdAndWalletStatus(agentUserID, OPEN);
		if(walletBalances.isEmpty()) {
			LOG_ERROR.info(create("Carteira não encontrada").add("agentId", agentUserID).build());
			throw new NotFoundException("Carteira não encontrada.");
		}

		WalletBalanceModel balanceBefore = walletBalances.get(0);
		WalletBalanceModel balance = walletBalances.get(0);
		
		if(betStaus.equals(REGISTERING)) {
			if(balance.getBudget().compareTo(betModel.getBetValue()) >= 0) {
				balance.setBudget(getDifference(betModel.getBetValue(), balance.getBudget()));
				balance.setTotalAmountReceived(getSum(balance.getTotalAmountReceived(), betModel.getBetValue()));
				WalletBalanceModel balanceActual = walletBalanceRepository.save(balance);
				walletBalanceTransactionService.createTransactionToBetRegistered(betModel.getBetValue(), balanceActual);
				generateHistory(balanceBefore, balanceActual);
			}else {
				LOG_ERROR.info(create("Orçamento insuficiente").add("saldo atual", balance.getBudget()).add("Valor da aposta", betModel).build());
				throw new BusinessException("Orçamento insuficiente");
			}
		}

		if(betStaus.equals(WON)) {
			balance.setTotalAmountPaid(getSum(balance.getTotalAmountPaid(), betModel.getProfitValue()));
			balance.setTotalCommissionAmount(getSum(balance.getTotalCommissionAmount(), betModel.getCommissionValue()));
			WalletBalanceModel balanceActual = walletBalanceRepository.save(balance);
			walletBalanceTransactionService.createTransactionToBetWon(betModel.getProfitValue(), betModel.getCommissionValue(), balanceActual);
			generateHistory(balanceBefore, balanceActual);
		}	
		
	}
	
	public List<WalletBalanceSchema> list(WalletStatusEnum walletStatusEnum){
		
		UserPrinciple principle = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<WalletBalanceSchema> walletBalances = new ArrayList<>();
		LOG_ACTION.info(create("Listando Balanços").add("userId", principle.getId()).build());
		
		List<WalletBalanceModel> balances = new ArrayList<>();
		if(Objects.nonNull(walletStatusEnum)) {
			if(UserProfileUtils.isAdminOrManager(principle)) {
				balances = walletBalanceRepository.findByWalletStatus(walletStatusEnum);
			}else {
				balances = walletBalanceRepository.findByWalletAgentUserIdAndWalletStatus(principle.getId(), walletStatusEnum);
			}
		}else {
			if(UserProfileUtils.isAdminOrManager(principle)) {
				balances = (List<WalletBalanceModel>) walletBalanceRepository.findAll();
			}else {
				balances = walletBalanceRepository.findByWalletAgentUserId(principle.getId());
			}
		}
		
		if(!balances.isEmpty()) {
			walletBalances = balances.stream().map(balance -> new WalletBalanceSchema(balance)).collect(Collectors.toList());
		}
		
		return walletBalances;
	}
	
	private void generateHistory(WalletBalanceModel balanceBefore, WalletBalanceModel balanceActual) {
		WalletBalanceHistoryModel history = new WalletBalanceHistoryModel(balanceBefore, balanceActual);
		walletBalanceHistoryRepository.save(history);
	}
	
	
	
}
