package org.whh.dao;

import org.springframework.data.repository.CrudRepository;
import org.whh.entity.CarInfo;

public interface CarInfoDao extends CrudRepository<CarInfo, Long>
{

}
