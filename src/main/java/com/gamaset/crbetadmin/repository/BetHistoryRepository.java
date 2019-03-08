package com.gamaset.crbetadmin.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetadmin.repository.entity.BetHistoryModel;

@Repository
public interface BetHistoryRepository  extends PagingAndSortingRepository<BetHistoryModel, Long> {

}
