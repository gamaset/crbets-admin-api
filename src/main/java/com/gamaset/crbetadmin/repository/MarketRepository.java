package com.gamaset.crbetadmin.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetadmin.repository.entity.MarketModel;

@Repository
public interface MarketRepository extends PagingAndSortingRepository<MarketModel, Long> {

}
