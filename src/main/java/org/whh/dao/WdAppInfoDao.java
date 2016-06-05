package org.whh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.WdAppInfo;

public interface WdAppInfoDao extends PagingAndSortingRepository<WdAppInfo, Long>,JpaSpecificationExecutor<WdAppInfo> {
	public List<WdAppInfo> getBySrcWdAppInfoId(Long srcWdAppInfoId);
	
}