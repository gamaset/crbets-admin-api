package com.gamaset.crbetadmin.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetadmin.repository.entity.MarketSelectionModel;

@Repository
public interface MarketSelectionRepository extends PagingAndSortingRepository<MarketSelectionModel, Long> {

	List<MarketSelectionModel> findBySelectionId(Long selectionId);

}
