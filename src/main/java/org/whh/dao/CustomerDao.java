package org.whh.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.Customer;

public interface CustomerDao extends PagingAndSortingRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
	@Query("from Customer c where c.isRecommend = ?1 and c.consumeLevel = ?2")
	Page<Customer> findUnRecommendCustomer(boolean isRecommend, int consumeLevel, Pageable pageable);

	@Query("from Customer c where c.isRecommend = ?1 and c.province like ?2 and c.consumeLevel = ?3")
	Page<Customer> findUnRecommendCustomerWithProvince(boolean isRecommend, String province, int consumeLevel,
			Pageable pageable);

	@Query(nativeQuery = true, value = "select * from customer c where c.qq_is_wei_xin is null and status is null limit 1")
	Customer findUnknowUser();

}