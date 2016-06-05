package org.whh.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.Partner;

public interface PartnerDao extends PagingAndSortingRepository<Partner, Long>,JpaSpecificationExecutor<Partner>
{
	Partner findByCustomerId(Long customerId);
}