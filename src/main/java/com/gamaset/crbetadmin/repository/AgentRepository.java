package com.gamaset.crbetadmin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetadmin.repository.entity.AgentModel;

@Repository
public interface AgentRepository extends PagingAndSortingRepository<AgentModel, Long> {

	Optional<AgentModel> findByIdAndManagerId(Long agentId, Long userManagerId);

	List<AgentModel> findByManagerId(Long userManagerId);

	Optional<AgentModel> findByUserId(Long id);


}
