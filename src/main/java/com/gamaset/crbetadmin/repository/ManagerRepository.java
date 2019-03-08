package com.gamaset.crbetadmin.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetadmin.repository.entity.ManagerModel;

@Repository
public interface ManagerRepository extends PagingAndSortingRepository<ManagerModel, Long> {

	public ManagerModel findByUserId(Long userId);
}
