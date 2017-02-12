package org.whh.dao;

import org.springframework.data.repository.CrudRepository;
import org.whh.entity.ConfigInfo;

public interface ConfigInfoDao extends CrudRepository<ConfigInfo, Long>
{

}
