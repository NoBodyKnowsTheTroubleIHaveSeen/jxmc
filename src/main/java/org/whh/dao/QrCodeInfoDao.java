package org.whh.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.whh.entity.QrCodeInfo;

public interface QrCodeInfoDao
		extends PagingAndSortingRepository<QrCodeInfo, Long>, JpaSpecificationExecutor<QrCodeInfo> {
	public QrCodeInfo findByActionNameAndSceneStr(String actionName, String sceneStr);
}
