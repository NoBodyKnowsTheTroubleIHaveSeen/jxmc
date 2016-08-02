package org.whh.dao;

import org.springframework.data.repository.CrudRepository;
import org.whh.entity.JokeRecord;

public interface JokeRecordDao extends CrudRepository<JokeRecord, Long> {
	JokeRecord findByJokeIdAndJokeType(String jokeId,Integer jokeType);
}
