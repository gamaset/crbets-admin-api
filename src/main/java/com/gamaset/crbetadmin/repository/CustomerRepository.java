package com.gamaset.crbetadmin.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.gamaset.crbetadmin.repository.entity.CustomerModel;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<CustomerModel, Long> {

	Optional<CustomerModel> findByUserId(Long id);

	Optional<CustomerModel> findByUserTaxId(String taxId);

}
