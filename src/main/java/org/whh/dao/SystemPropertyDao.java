package org.whh.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.SystemProperty;

public interface SystemPropertyDao extends PagingAndSortingRepository<SystemProperty, Long> {
}