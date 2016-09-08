package org.whh.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.Contents;

public interface ContentsDao extends PagingAndSortingRepository<Contents, Long> {
	Contents findByContent(String content);

	Contents findBySrcIdAndSrcUuid(Integer srcId, String uuid);
}