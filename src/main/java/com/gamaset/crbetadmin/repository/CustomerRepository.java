package com.gamaset.crbetadmin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetadmin.repository.entity.CustomerModel;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<CustomerModel, Long> {

	Optional<CustomerModel> findById(Long id);

	Optional<CustomerModel> findByTaxId(String taxId);

	List<CustomerModel> findByAgentId(Long userId);

}
