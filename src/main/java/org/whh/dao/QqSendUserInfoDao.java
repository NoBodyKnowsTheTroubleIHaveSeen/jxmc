package org.whh.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.whh.entity.QqSendUserInfo;

public interface QqSendUserInfoDao extends CrudRepository<QqSendUserInfo, Long> {
	List<QqSendUserInfo> findByGroupUinAndUinAndContent(String groupUin, String uin, String content);

	QqSendUserInfo findByUinAndContent(String uin, String content);
}
