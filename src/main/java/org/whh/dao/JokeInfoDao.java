package org.whh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.whh.entity.JokeInfo;

public interface JokeInfoDao extends CrudRepository<JokeInfo, Long> {

	@Query("from JokeInfo j where j.auditStatus = 1 or j.auditStatus is null  and isUsed != 1  order by agreeCount desc")
	List<JokeInfo> getNextContent();

	@Query("select a from JokeInfo as a,JokeRecord as b where a.jokeId = b.jokeId and a.auditStatus = 1 or a.auditStatus is null and b.jokeType = 2 order by a.agreeCount desc")
	List<JokeInfo> getNextContent2();
}
