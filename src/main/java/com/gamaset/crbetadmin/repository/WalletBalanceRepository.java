package com.gamaset.crbetadmin.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetadmin.repository.entity.WalletBalanceModel;
import com.gamaset.crbetadmin.repository.entity.WalletStatusEnum;

@Repository
public interface WalletBalanceRepository extends PagingAndSortingRepository<WalletBalanceModel, Long> {

	List<WalletBalanceModel> findByWalletAgentUserIdAndWalletStatus(Long agentUserID, WalletStatusEnum open);

	List<WalletBalanceModel> findByWalletAgentUserId(Long id);

	List<WalletBalanceModel> findByWalletStatus(WalletStatusEnum walletStatusEnum);

}
