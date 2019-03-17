package com.gamaset.crbetadmin.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetadmin.repository.entity.CompetitionModel;

@Repository
public interface CompetitionRepository extends PagingAndSortingRepository<CompetitionModel, Long> {

	List<CompetitionModel> findAllByOrderByEventTypeIdAscDescriptionAsc();

	List<CompetitionModel> findByEventTypeId(Long eventTypeId);

}
