package org.whh.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.Code;

public interface CodeDao extends PagingAndSortingRepository<Code, Long> {
	List<Code> findByCodeGroup(String codeGroup);
}