package com.gamaset.crbetadmin.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetadmin.repository.entity.WalletModel;
import com.gamaset.crbetadmin.repository.entity.WalletStatusEnum;

@Repository
public interface WalletRepository extends PagingAndSortingRepository<WalletModel, Long> {

	List<WalletModel> findByAgentUserId(Long id);

	List<WalletModel> findByAgentUserIdAndStatus(Long agentID, WalletStatusEnum status);

	List<WalletModel> findByStatus(WalletStatusEnum walletStatus);


}
