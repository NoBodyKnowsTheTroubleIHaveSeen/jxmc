package org.whh.dao;

import org.springframework.data.repository.CrudRepository;
import org.whh.entity.JokeInfo;

public interface JokeInfoDao extends CrudRepository<JokeInfo, Long> {

}
