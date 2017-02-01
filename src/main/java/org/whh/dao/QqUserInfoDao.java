package org.whh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.whh.entity.QqUserInfo;

public interface QqUserInfoDao extends CrudRepository<QqUserInfo, Long> {

	QqUserInfo findByUin(String uin);
	
	List<QqUserInfo> findByGroupUin(String groupUin);

	@Query("from QqUserInfo q group by groupUin")
	List<QqUserInfo> findGroupFristByGroupUin();
	
	@Query("from QqUserInfo q where canSend = 1")
	List<QqUserInfo> findCanSend();
}