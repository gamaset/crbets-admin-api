package com.gamaset.crbetadmin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetadmin.repository.entity.BetModel;

@Repository
public interface BetRepository  extends PagingAndSortingRepository<BetModel, Long> {


	List<BetModel> findByCustomerAgentId(Long userAgentId);

	Optional<BetModel> findByIdAndCustomerAgentId(Long betId, Long userAgentId);

}