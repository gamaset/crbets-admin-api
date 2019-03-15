package com.gamaset.crbetadmin.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetadmin.repository.entity.EventTypeModel;

@Repository
public interface EventTypeRepository extends PagingAndSortingRepository<EventTypeModel, Long> {


}
