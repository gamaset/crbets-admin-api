package com.gamaset.crbetadmin.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetadmin.repository.entity.WalletBalanceTransactionModel;

@Repository
public interface WalletBalanceTransactionRepository extends PagingAndSortingRepository<WalletBalanceTransactionModel, Long> {

	List<WalletBalanceTransactionModel> findByWalletBalanceId(Long balanceId);

}
