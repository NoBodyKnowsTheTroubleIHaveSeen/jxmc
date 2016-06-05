package org.whh.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.SystemConstant;

public interface SystemConsotantDao extends PagingAndSortingRepository<SystemConstant, Long> {
}