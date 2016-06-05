package org.whh.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.Email;

public interface EmailDao extends PagingAndSortingRepository<Email, Long>,JpaSpecificationExecutor<Email> {
}