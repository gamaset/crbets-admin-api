package com.gamaset.crbetadmin.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetadmin.repository.entity.WalletBalanceHistoryModel;

@Repository
public interface WalletBalanceHistoryRepository extends PagingAndSortingRepository<WalletBalanceHistoryModel, Long> {

}
