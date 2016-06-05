package org.whh.dao;

import org.springframework.data.repository.CrudRepository;
import org.whh.entity.QQGroupInfo;

public interface QQGroupInfoDao extends CrudRepository<QQGroupInfo, Long>
{
	public QQGroupInfo findByGroupCode(long groupCode);
}
