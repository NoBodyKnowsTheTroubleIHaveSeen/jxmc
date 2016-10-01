package org.whh.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.Contents;

public interface ContentsDao extends PagingAndSortingRepository<Contents, Long> {
	Contents findByContent(String content);

	Contents findBySrcIdAndSrcUuid(Integer srcId, String uuid);
	
	@Query("select max(srcUuid) from Contents c where c.srcId = ?1")
	String findBySrcId(Integer srcId);
}