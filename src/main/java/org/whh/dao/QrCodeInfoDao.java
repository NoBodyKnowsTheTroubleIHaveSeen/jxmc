package org.whh.dao;

import org.springframework.data.repository.CrudRepository;
import org.whh.entity.QrCodeInfo;

public interface QrCodeInfoDao extends CrudRepository<QrCodeInfo, Long> {
	public QrCodeInfo findByActionNameAndSceneStr(String actionName, String sceneStr);
}
