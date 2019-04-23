package com.gamaset.crbetadmin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetadmin.repository.entity.BetModel;
import com.gamaset.crbetadmin.repository.entity.BetStatusEnum;

@Repository
public interface BetRepository  extends PagingAndSortingRepository<BetModel, Long> {


	@Query(value = "SELECT b FROM BetModel b ORDER BY b.createAt DESC")
	List<BetModel> findAllOrderByCreateAtDesc();

	List<BetModel> findByCustomerAgentIdOrderByCreateAtDesc(Long userAgentId);

	Optional<BetModel> findByIdAndCustomerAgentId(Long betId, Long userAgentId);

	List<BetModel> findByStatus(BetStatusEnum status);


}
