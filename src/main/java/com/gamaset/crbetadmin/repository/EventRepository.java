package com.gamaset.crbetadmin.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetadmin.repository.entity.EventModel;

@Repository
public interface EventRepository extends PagingAndSortingRepository<EventModel, Long> {

	List<EventModel> findByBetId(Long betId);

}
