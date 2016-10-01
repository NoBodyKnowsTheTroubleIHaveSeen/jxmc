package org.whh.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.WxKeywordMap;

public interface WxKeywordMapDao extends PagingAndSortingRepository<WxKeywordMap, Long> {
	WxKeywordMap findByKeyword(String keyword);
}
